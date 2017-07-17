package controllers;

import play.Logger;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import util.CSVFileReader;
import util.Utility;

import javax.inject.Inject;
import java.io.File;
import java.io.InputStream;

import java.util.List;


/**
 * Created by anuradha_uduwage
 */
public class ApplicationController extends Controller {

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
        try {
            List<Object> recordsList = null;
            MultipartFormData<File> body = request().body().asMultipartFormData();
            FilePart<File> csvFile = body.getFile("csvfile");

            if (csvFile != null && csvFile.getFilename().contains(".csv")) {
                CSVFileReader fileReader = CSVFileReader.getReaderInstance();
                try {
                    File file = csvFile.getFile();
                    InputStream inputStream = fileReader.uploadFile(file);
                    recordsList = fileReader.parseFile(inputStream);
                } catch (Exception ex) {
                    Logger.error("Exception occured::: " + ex.getMessage());

                }
            }

            if (recordsList != null)
                return ok(Utility.createResponse(recordsList, true));
            else
                return badRequest(Utility.createResponse("There were no records in the list", false));
        } catch (Exception ex) {
            return internalServerError();
        }
    }

}
