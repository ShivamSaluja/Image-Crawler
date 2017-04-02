import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author rusty-sj
 * 
 * A simple Java code to store images from a URL to your local system
 * 
 * Uses Jsoup library
 *
 */
public class DownloadImagesWithJsoup {

	public static void main(String[] args) {
		Document doc;

		try {
			doc = Jsoup.connect("https://www.zomato.com/pune/bubble-bee-aundh/photos").get();	// URL from where you want to collect photos
			Elements images = doc.select(".photos_container_load_more a img");					// Simple selector to spot all img tags in html Doc
			for (Element image : images) {					
				//System.out.println("src : " + image.attr("data-original").split("\\?")[0]);
				String imgURL = image.attr("data-original").split("\\?")[0];					// Collecting data-original(or src) attribute of img tag (I have split it on ? so as to remove extra URL parameters such as fit=, crop=)
				new File("E:/CrawlerImages").mkdirs();											// Create new directory in you system where you want to store images
				URL url = new URL(imgURL);														
				InputStream in = url.openConnection().getInputStream();			
				Files.copy(in, Paths.get("E:/CrawlerImages/" + imgURL.substring(imgURL.lastIndexOf("/"))), // Save image in created folder with some name
						StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}