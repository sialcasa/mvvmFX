package de.saxsys.mvvmfx.examples.books.backend;

import com.theoryinpractise.halbuilder.api.ContentRepresentation;
import com.theoryinpractise.halbuilder.api.Link;
import com.theoryinpractise.halbuilder.api.ReadableRepresentation;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderReaderSupport;
import org.jboss.resteasy.client.jaxrs.cache.BrowserCacheFeature;
import org.jboss.resteasy.plugins.interceptors.encoding.AcceptEncodingGZIPFilter;
import org.jboss.resteasy.plugins.interceptors.encoding.GZIPDecodingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import static com.theoryinpractise.halbuilder.api.RepresentationFactory.*;

public class LibraryServiceImpl implements Serializable, LibraryService {

    private static final long serialVersionUID = 1L;
    private static final String BASE_URL = "http://localhost:8080/rest";
    private static final Logger LOGGER = LoggerFactory.getLogger(LibraryServiceImpl.class);

    private Client apiClient;

    public LibraryServiceImpl(){
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

    @Override
    public Book lend(String lendTo, Book detailBook, Consumer<Error> errorCallback) {
        LOGGER.debug("Lend book {} to member {}", detailBook.getTitle(), lendTo);
        try {
            Integer.valueOf(lendTo);
        } catch (NumberFormatException e) {
            errorCallback.accept(Error.error("Invalid MemberID", "MemberID is not a number"));
            return null;
        }
        Response response =
                apiClient.target(detailBook.getRelLend().getHref())
                        .request(HAL_JSON)
                        .put(Entity.json("{\"memberId\":" + lendTo + "}"));
        ContentRepresentation rep = response.readEntity(ContentRepresentation.class);
        if (response.getStatus() >= 400) {
            String message = (String) rep.getValue("title");
            String detail = (String) rep.getValue("detail", null);
            LOGGER.error("{} {} {}", response.getStatus(), message, detail);
            errorCallback.accept(Error.error(message, detail));
            return null;
        } else {
            detailBook = toBook(rep);
            LOGGER.debug("Book {} lent to member {}", detailBook.getTitle(),
                    detailBook.getBorrower());
            return detailBook;
        }
    }

    @Override
    public Book takeBack(Book detailBook, Consumer<Error> errorCallback) {
        LOGGER.debug("Return book {} from member {}", detailBook.getTitle(),
                detailBook.getBorrower());
        Response response =
                apiClient.target(
                        HalUtil.replaceParam(detailBook.getRelReturn().getHref(), detailBook.getBorrower()
                            .toString()))
                        .request(HAL_JSON)
                        .delete();
        ContentRepresentation rep = response.readEntity(ContentRepresentation.class);
        if (response.getStatus() >= 400) {
            String message = (String) rep.getValue("title");
            String detail = (String) rep.getValue("detail", null);
            LOGGER.error("{} {}", message, detail);
            errorCallback.accept(Error.error(message, detail));
            return null;
        } else {
            LOGGER.debug("Book {} returned", detailBook.getTitle());
            return toBook(rep);
        }
    }

    private Book toBook(ReadableRepresentation rep) {
        Book book =
                new Book(
                        rep.getResourceLink().getHref(), (String) rep.getValue("title", null),
                        (String) rep.getValue("author", null), (String) rep.getValue("description",
                                null), rep.getLinkByRel("lib:lend"), rep.getLinkByRel("lib:return"));
        // add borrower if available
        List<? extends ReadableRepresentation> borrowerResource = rep.getResourcesByRel("borrower");
        if (1 == borrowerResource.size())
            book.setBorrower(Integer.valueOf((String) borrowerResource.get(0).getValue("id")));
        return book;
    }
}
