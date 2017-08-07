package controllers;

import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import util.CSVFileReader;
import util.CSVReader;
import util.Utility;

import javax.inject.Inject;
import java.io.File;
import java.io.InputStream;

import java.util.List;


/**
 * Created by anuradha_uduwage
 */
public class FileUploadController extends Controller {

    /**
     * An action that renders login credentials to identify successful
     * login action.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    @Inject
    FormFactory formFactory;

    public Result index() {
        return ok("Hello");
    }

    public Result uploadCSVFile() {

        Form<FileUploadForm> fileUploadFormForm = formFactory.form(FileUploadForm.class).bindFromRequest();

        try {
            MultipartFormData<File> body = request().body().asMultipartFormData();
            FilePart<File> csvFile = body.getFile("csvfile");
            CSVReader csvReader;
            if (csvFile != null && csvFile.getFilename().contains(".csv")) {
                try {
                    csvReader = new CSVReader(csvFile.getFilename());
                    csvReader.parseFile();

                } catch (Exception ex) {
                    Logger.error("Exception occured::: " + ex.getMessage());
                    return status(400,  "Missing file");
                }
            }
        } catch (Exception ex) {
            return internalServerError();
        }
        return ok(Utility.createResponse("File was successfully loaded and parse", true));
    }

    /**
     * Static class for the form.
     */
    public static class FileUploadForm {

        @Constraints.Required
        public String filePath;
    }

}