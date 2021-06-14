package ua.goit.controller;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Lists;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ua.goit.model.StudyBlockState;
import ua.goit.model.TaskBlock;
import ua.goit.util.PropertiesLoader;

@Slf4j
public class GoogleSheetsController {

    @SneakyThrows
    public List<StudyBlockState> read() {

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadSheetId = PropertiesLoader.getProperty("google.sheets.spreadsheets.id");

        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JacksonFactory.getDefaultInstance(), getCredentials())
                .setApplicationName(PropertiesLoader.getProperty("application.name"))
                .build();
        Spreadsheet sheetMetadata = service.spreadsheets().get(spreadSheetId).execute();

        List<StudyBlockState> studyBlockStates = new ArrayList<>();
        for (Sheet sheet : sheetMetadata.getSheets()) {
            List<List<Object>> values = service.spreadsheets().values().get(spreadSheetId,
                    PropertiesLoader.getProperty("google.sheets.range")).execute().getValues();
            if (values != null && !values.isEmpty()) {
                List<TaskBlock> taskBlocks = values.stream()
                        .filter(row -> !row.isEmpty())
                        .map(row -> TaskBlock.builder()
                                .question(String.valueOf(row.get(0)))
                                .answer(String.valueOf(row.get(1)))
                                .video(String.valueOf(row.get(2)))
                                .build())
                        .collect(Collectors.toList());
               studyBlock.questionsLists.add(taskBlock);
                }
            }
        studyBlocks.add(studyBlock);
        }
  
    private HttpRequestInitializer getCredentials() throws IOException {
        try (InputStream inputStream = PropertiesLoader.getInputStream(PropertiesLoader.getProperty("telegram.credential.file.path"))) {
            return new HttpCredentialsAdapter(GoogleCredentials.fromStream(inputStream)
                    .createScoped(Lists.newArrayList(Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY))));
        }
    }

}
