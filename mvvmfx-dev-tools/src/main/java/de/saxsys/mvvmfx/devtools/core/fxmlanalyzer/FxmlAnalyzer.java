package de.saxsys.mvvmfx.devtools.core.fxmlanalyzer;


import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.util.Either;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.warnings.ControllerClassNotFoundWarning;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.warnings.InvalidXmlWarning;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.warnings.SrcPathNotFoundWarning;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.warnings.Warning;
import de.saxsys.mvvmfx.internal.viewloader.FxmlViewLoaderUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FxmlAnalyzer {


	public static ViewNode loadViewTree(Class<? extends FxmlView> rootViewType) {
		Objects.requireNonNull(rootViewType);
		final String fxmlPath = FxmlViewLoaderUtils.resolveFxmlPath(rootViewType);

		return loadFromPath(fxmlPath);
	}

	private static DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();



	private static ViewNode loadFromPath(String relativePath) {
		final String relativeFxmlPath = relativePath.replace('\\', '/');

		ViewNode viewNode = new ViewNode(relativeFxmlPath);

		URL fxmlUrl = FxmlAnalyzer.class.getResource(relativeFxmlPath);

		if(fxmlUrl == null) {
			viewNode.addWarnings(new SrcPathNotFoundWarning());
			return viewNode;
		}


		Either<Document, Warning> documentEither = loadDocument(fxmlUrl);

		if(documentEither.isRight()) {
			viewNode.addWarnings(documentEither.getRightUnsafe());
		} else {
			Document document = documentEither.getLeftUnsafe();


			// search for child views
			List<ViewNode> children = loadSubViewNodes(relativeFxmlPath, document);
			viewNode.addChildren(children);


			// search for a controller class
			String controllerFQN = document.getDocumentElement().getAttribute("fx:controller");

			if(controllerFQN != null) {
				Either<Class, Warning> controllerClass = getControllerClassFromFQN(controllerFQN);

				if(controllerClass.isLeft()) {

					Class controller = controllerClass.getLeftUnsafe();
					viewNode.setControllerType(controller);
				} else {
					// a controller class was specified but couldn't be found
					viewNode.addWarnings(controllerClass.getRightUnsafe());
				}
			}


			// search for ViewModel class
			// todo
		}

		return viewNode;
	}

	private static List<ViewNode> loadSubViewNodes(String relativeFxmlPath, Document document) {
		// source paths of fx:include elements
		List<String> includes = resolveFxIncludesFromDoc(document);

		Path relativeRootPath = Paths.get(relativeFxmlPath);

		return includes.stream()
				.map(relativeRootPath::resolveSibling)
				// recursive method call
				.map(subViewPath -> loadFromPath(subViewPath.toString()))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	private static Either<Class, Warning> getControllerClassFromFQN(String fullyQualifiedName) {
		try {
			return Either.left(FxmlAnalyzer.class.getClassLoader().loadClass(fullyQualifiedName));
		} catch (ClassNotFoundException e) {
			return Either.right(new ControllerClassNotFoundWarning(fullyQualifiedName));
		}
	}

	private static List<String> resolveFxIncludesFromDoc(Document document) {
		NodeList fxIncludes = document.getElementsByTagName("fx:include");
		if(fxIncludes.getLength() == 0) {
			return Collections.emptyList();
		}

		Stream<Node> nodeStream = IntStream.range(0, fxIncludes.getLength())
				.mapToObj(fxIncludes::item);

		return nodeStream.filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
				.map(node -> (Element) node)
				.map(element -> element.getAttribute("source"))
				.filter(Objects::nonNull)
				.filter(source -> !source.trim().isEmpty())
				.collect(Collectors.toList());
	}

	private static Either<Document, Warning> loadDocument(URL path) {
		try {
			DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();

			InputStream inputStream = path.openStream();

			Document parsedDocument = documentBuilder.parse(inputStream);
			parsedDocument.getDocumentElement().normalize();

			return Either.left(parsedDocument);
		} catch (ParserConfigurationException e) {
			// this case shouldn't happen.
			// It would be a mean a bug in the program itself and not the document of the user.
			throw new IllegalStateException(e);
		} catch (IOException e) {
			return Either.right(new SrcPathNotFoundWarning());
		} catch (SAXException e) {
			return Either.right(new InvalidXmlWarning());
		}
	}
}
