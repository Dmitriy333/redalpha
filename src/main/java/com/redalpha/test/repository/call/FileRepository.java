package com.redalpha.test.repository.call;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.redalpha.test.model.call.Call;

@Repository
public class FileRepository implements CallRepository {

    @Value("${call.storage.location}")
    private String callStorageLocation;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static final String FILE_PATH_PATTERN = "%s/%s";
    private static final String FILE_NAME_LASTNAME_FIRSTNAME_PATTERN = "%s_%s.txt";
    private static final String FILE_NAME_LASTNAME_PATTERN = "%s.txt";
    private static final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    public Call writeCall(final Call call) throws CallRepositoryException {
        try {
            readWriteLock.writeLock().lock();
            call.setTime(new Date());

            final List<String> callProperties = Arrays.asList(call.getPhone(), call.getLastname(), call.getFirstname(),
                    dateFormat.format(call.getTime()));

            Path filePath = Paths.get(String.format(FILE_PATH_PATTERN, callStorageLocation,
                    generateCallName(call.getLastname(), call.getFirstname())));

            File file = filePath.toFile();
            if (file.exists() != true) {
                file.createNewFile();
            }

            Files.write(filePath, callProperties, StandardCharsets.UTF_8);
            readWriteLock.writeLock().unlock();
            return call;
        } catch (IOException e) {
            readWriteLock.writeLock().unlock();
            throw new CallRepositoryException("Can't write call into disk space", e);
        }
    }

    @Override
    public List<Call> readCalls() throws CallRepositoryException {

        try {
            readWriteLock.readLock().lock();
            final List<Call> callList = new ArrayList<Call>();
            final File folder = new File(callStorageLocation);

            for (final File file : folder.listFiles()) {
                List<String> fileContent = new ArrayList<String>();
                if (file.isDirectory() != true) {
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            fileContent.add(line);
                        }
                    }
                }
                callList.add(createCall(fileContent));
            }

            readWriteLock.readLock().unlock();
            return callList;
        } catch (IOException e) {
            readWriteLock.readLock().unlock();
            throw new CallRepositoryException("Can not read from disk space", e);
        }

    }

    private Call createCall(List<String> callProperties) throws CallRepositoryException {
        Call call = new Call();
        call.setPhone(callProperties.get(0));
        call.setFirstname(callProperties.get(1));
        call.setLastname(callProperties.get(2));
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        try {
            Date date = formatter.parse(callProperties.get(3));
            call.setTime(date);
        } catch (ParseException e) {
            throw new CallRepositoryException("Date format is incorrect.", e);
        }
        return call;
    }

    private String generateCallName(final String lastname, final String firstname) {
        if (StringUtils.isEmpty(firstname)) {
            return String.format(FILE_NAME_LASTNAME_PATTERN, lastname.toUpperCase());
        }

        return String.format(FILE_NAME_LASTNAME_FIRSTNAME_PATTERN, lastname.toUpperCase(), firstname.toUpperCase());
    }
}
