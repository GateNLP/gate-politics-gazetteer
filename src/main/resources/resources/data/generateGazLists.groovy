BufferedReader fin = new BufferedReader(new FileReader(args[0]+".tsv"));

String[] headers = fin.readLine().split("\t");

row = fin.readLine();


int hI = findIndex(headers,"twitter_handle");
int iI = findIndex(headers,"twitter_id");
int nI = findIndex(headers,"name");

if (hI == -1 || iI == -1 || nI == -1) {
	System.err.println("Can't locate all three necessary columns");
	System.exit(1);
}

try (
	PrintWriter hOut = new PrintWriter(new FileWriter(args[0]+"_accounts.lst"));
	PrintWriter iOut = new PrintWriter(new FileWriter(args[0]+"_ids.lst"));
	PrintWriter nOut = new PrintWriter(new FileWriter(args[0]+"_names.lst"));
) {

	while (row != null) {

		row = row.split("\t");
		
		StringBuilder features = new StringBuilder();
		
		for (int i = 0 ; i < row.length ; ++i) {
			if (!row[i].trim().equals(""))
				features.append("\t").append(headers[i]).append("=").append(row[i]);
		}
		
		// TODO what happens if the row for the entry is blank?
		hOut.println(row[hI]+features);
		iOut.println(row[iI]+features);
		nOut.println(row[nI]+features);
		

		row = fin.readLine();
	}

}


private int findIndex(String[] headers, String name) {

	for (int i = 0 ; i < headers.length; ++i) {
		if (headers[i].equals(name)) return i;
	}
	
	return -1;
}
