package de.saxsys.mvvmfx.examples.books.backend;

import static com.theoryinpractise.halbuilder.api.RepresentationFactory.HAL_JSON;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.cache.BrowserCacheFeature;
import org.jboss.resteasy.plugins.interceptors.encoding.AcceptEncodingGZIPFilter;
import org.jboss.resteasy.plugins.interceptors.encoding.GZIPDecodingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.theoryinpractise.halbuilder.api.ContentRepresentation;
import com.theoryinpractise.halbuilder.api.Link;
import com.theoryinpractise.halbuilder.api.ReadableRepresentation;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderReaderSupport;

public class LibraryServiceImpl implements Serializable, LibraryService {
	
	private static final long serialVersionUID = 1L;
	private static final String BASE_URL = "http://localhost:8080/rest";
	private static final Logger LOGGER = LoggerFactory.getLogger(LibraryServiceImpl.class);
	
	private Client apiClient;
	
	public LibraryServiceImpl() {
		apiClient =
				ClientBuilder.newClient()
						.register(JaxRsHalBuilderReaderSupport.class)
						.register(BrowserCacheFeature.class)
						.register(GZIPDecodingInterceptor.class)
						.register(AcceptEncodingGZIPFilter.class);
	}
	
	@Override
	public List<Book> search(String query, Consumer<Error> errorCallback) {
		try {
			// home
			Response responseHome = apiClient.target(BASE_URL).request(HAL_JSON).get();
			ContentRepresentation repHome = responseHome.readEntity(ContentRepresentation.class);
			// search
			Link searchLink = repHome.getLinkByRel("lib:search");
			String href = HalUtil.replaceParam(searchLink.getHref(), query);
			Response responseSearch = apiClient.target(href).request(HAL_JSON).get();
			ContentRepresentation repSearch =
					responseSearch.readEntity(ContentRepresentation.class);
			Collection<ReadableRepresentation> resultSet =
					repSearch.getResourceMap().get("lib:book");
			List<Book> books = new ArrayList<>();
			if (null != resultSet) {
				for (ReadableRepresentation rep : resultSet) {
					Book book = toBook(rep);
					if (null == book.getTitle() || null == book.getAuthor()) {
						// get book
						Response response =
								apiClient.target(book.getHref()).request(HAL_JSON).get();
						ContentRepresentation repBook =
								response.readEntity(ContentRepresentation.class);
						book = toBook(repBook);
					}
					books.add(book);
				}
			}
			return books;
		} catch (Throwable e) {
			LOGGER.error("Error during search", e);
			errorCallback.accept(Error.error("Error during search", e.getMessage()));
			return new ArrayList<>();
		}
	}
	
	@Override
	public Book showDetails(Book book, Consumer<Error> errorCallback) {
		LOGGER.debug("Show details for book at {}", book.getHref());
		try {
			Response response = apiClient.target(book.getHref()).request(HAL_JSON).get();
			ContentRepresentation rep = response.readEntity(ContentRepresentation.class);
			return toBook(rep);
		} catch (Exception e) {
			LOGGER.error("Error retrieving book", e);
			errorCallback.accept(Error.error("Error retrieving book", e.getMessage()));
			return null;
		}
	}
	
	
	private Book toBook(ReadableRepresentation rep) {
		return new Book(
				rep.getResourceLink().getHref(), (String) rep.getValue("title", null),
				(String) rep.getValue("author", null), (String) rep.getValue("description",
						null));
	}
}
