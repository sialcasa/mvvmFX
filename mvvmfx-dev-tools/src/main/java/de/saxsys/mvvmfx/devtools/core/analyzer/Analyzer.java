package de.saxsys.mvvmfx.devtools.core.analyzer;

import de.saxsys.mvvmfx.*;
import de.saxsys.mvvmfx.internal.viewloader.FxmlViewLoaderUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Analyzer {

    private static DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();


    private static FxmlNode parseFXMLFile(String fxmlPath) {
        fxmlPath = fxmlPath.replace('\\', '/');
        URL fxmlURL = Analyzer.class.getResource(fxmlPath);

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(fxmlURL.openStream());

            Class<?> controller = getController(document);

            Path relativeRootPath = Paths.get(fxmlPath).normalize();
            List<FxmlNode> fxIncludes = getFxIncludes(document).stream()
                    .map(relativeRootPath::resolveSibling)
                    .map(Path::toString)
                    .map(Analyzer::parseFXMLFile)
                    .collect(Collectors.toList());

            if (FxmlView.class.isAssignableFrom(controller)) {
                Class<? extends FxmlView> codeBehind = (Class<? extends FxmlView>) controller;
                Class<? extends ViewModel> viewModel = getViewModel(codeBehind);


                List<Class<? extends Scope>> providedScopeList = getProvidedScopes(viewModel);
                List<Class<? extends Scope>> scopeList = getScopes(viewModel);

                return new MvvmFxmlNode(fxmlPath, codeBehind, fxIncludes, viewModel, scopeList, providedScopeList);
            } else {
                return new FxmlNodeImpl(fxmlPath, controller, fxIncludes);
            }


        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return new FxmlNodeImpl(fxmlPath, null, Collections.emptyList());
    }

    private static Class<?> getController(Document document) {

        String controller = document.getDocumentElement().getAttribute("fx:controller");

        Class<?> aClass = null;
        try {
            aClass = Class.forName(controller);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return aClass;
    }

    private static Class<? extends ViewModel> getViewModel(Class<? extends FxmlView> codeBehind) {

        return Stream.of(codeBehind.getAnnotatedInterfaces()) // get stream of all implemented interfaces of the codeBehind class
                .filter(annotatedType -> annotatedType.getType() instanceof ParameterizedType)  // check whether this interface is a parameterized interface (generics)
                .map(annotatedType -> (ParameterizedType) annotatedType.getType())
                .filter(parameterizedType -> FxmlView.class.isAssignableFrom((Class) parameterizedType.getRawType())) // check whether we have the FxmlView interface (there could be more interfaces implemented by the ViewModel)
                .flatMap(parameterizedType -> Stream.of(parameterizedType.getActualTypeArguments()))  // get all generic type arguments of the FxmlView interface
                .map(typeArgument -> (Class) typeArgument) // type arguments are of type "Type" which is a "Class"
                .filter(ViewModel.class::isAssignableFrom) // check whether the generic type argument is our ViewModel
                .findFirst()
                .map(aClass -> (Class<? extends ViewModel>) aClass)
                .orElse(null);


    }

    private static List<String> getFxIncludes(Document document) {
        NodeList fxIncludes = document.getElementsByTagName("fx:include");
        if (fxIncludes.getLength() == 0) {
            return Collections.emptyList();
        }

        return IntStream.range(0, fxIncludes.getLength())
                .mapToObj(fxIncludes::item)
                .filter(node -> node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE)
                .map(node -> (Element) node)
                .map(element -> element.getAttribute("source"))
                .filter(Objects::nonNull)
                .filter(source -> !source.trim().isEmpty())
                .collect(Collectors.toList());
    }

    private static List<Class<? extends Scope>> getProvidedScopes(Class<? extends ViewModel> viewModel) {

        List<Class<? extends Scope>> providedScopeList = new ArrayList<>();

        //@ScopeProvider(scopes = {...})
        Stream.of(viewModel.getAnnotations())
                .filter(annotation -> ScopeProvider.class.isAssignableFrom(annotation.getClass())) //check if the ViewModel class has a ScopeProvider Annotation
                .findFirst()
                .map(annotation -> (ScopeProvider) annotation)
                .ifPresent(scopeProvider -> Collections.addAll(providedScopeList, scopeProvider.scopes())); //add Scope Classes to providedScopeList;

        return providedScopeList;


    }

    private static List<Class<? extends Scope>> getScopes(Class<? extends ViewModel> viewModel) {

        return Stream.of(viewModel.getDeclaredFields()) // Stream of all Fields in the ViewModel class
                .filter(field -> field.isAnnotationPresent(InjectScope.class)) // Check for @InjectScope
                .filter(field -> Scope.class.isAssignableFrom(field.getType())) // Checks for Fields of type Scope
                .map(field -> (Class<? extends Scope>) field.getType())
                .collect(Collectors.toList());
    }


    public static FxmlNode parseFXML(Class<?> viewClass) {
        String fxmlName = FxmlViewLoaderUtils.resolveFxmlPath(viewClass);
        return parseFXMLFile(fxmlName);
    }
}
