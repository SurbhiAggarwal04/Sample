package com.controller;

import java.io.File;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
 


import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloWorld {
	  ArrayList<String> nounPhrases = new ArrayList<String>();
	ArrayList<String> result=new ArrayList<String>();
	@RequestMapping(value = "/pras", method = RequestMethod.GET)
    public String login(Model model) throws ServletException, IOException 
	{
		String sentence = "Sita and an the Gita";
		
		
		InputStream modelInParse = null;
		FileInputStream fs=null;
		try {
			//load chunking model
			 fs = new FileInputStream(new File("C:\\Users\\surbhi\\Documents\\workspace-sts-3.6.3.SR1\\FirstMavenProject\\src\\main\\java\\com\\controller\\en-parser-chunking.bin"));
			//modelInParse = new InputStream(fs); //from http://opennlp.sourceforge.net/models-1.5/
		//	modelInParse = modelInParse.replaceAll("\\", "/");
			ParserModel model1 = new ParserModel(fs);
			
			//create parse tree
			Parser parser = ParserFactory.create(model1);
			Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);
			
			//call subroutine to extract noun phrases
			for (Parse p : topParses)
				getNounPhrases(p);
			
			//print noun phrases
			for (String s : nounPhrases)
			   result.add(s);
			
			model.addAttribute("result", result);
			
			//The Call
			//the Wild?
			//The Call of the Wild? //punctuation remains on the end of sentence
			//the author of The Call of the Wild?
			//the author
		}
		catch (Exception e) {
		  e.printStackTrace();
		}
		finally {
		  if (modelInParse != null) {
		    try {
		    	modelInParse.close();
		    }
		    catch (IOException e) {
		    }
           
        
    }
}
		System.out.println(result);
		 return "helloWorld1";
	}
	public  void getNounPhrases(Parse p) {
		
	    if (p.getType().equals("NP")) { //NP=noun phrase
	         nounPhrases.add(p.toString());
	    }
	    for (Parse child : p.getChildren())
	         getNounPhrases(child);
	}
}
