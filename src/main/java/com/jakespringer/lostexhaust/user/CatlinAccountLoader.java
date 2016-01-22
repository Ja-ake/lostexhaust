package com.jakespringer.lostexhaust.user;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.jakespringer.lostexhaust.error.InvalidAccountElementException;
import com.jakespringer.lostexhaust.error.InvalidEmailException;
import com.jakespringer.lostexhaust.error.InvalidNumberException;
import com.jakespringer.lostexhaust.error.ParsingException;

public class CatlinAccountLoader {
	public List<Account> loadAccounts(String filename) throws IOException, InvalidAccountElementException, ParsingException, InvalidEmailException, InvalidNumberException {
		List<Account> accountList = new ArrayList<>();
		
		List<String> accountLines = Files.readAllLines(Paths.get(URI.create(filename)), Charset.forName("UTF8"));
		for (String accountLine : accountLines) {
			String[] token = accountLine.split("\\t");
			String id = token[0];
			String username = token[1];
			String name = token[2];
			String contactString = token[3];
			String notes = token[4];
			String placeid = token[5];
			String tags = token[6];
			
			Account account = new Account(id);
			account.setUsername(username);
			account.setName(name);
			account.setContact(AccountDbService.getInstance().parseContact(contactString));
		}
		
		return Collections.unmodifiableList(accountList);
	}
}
