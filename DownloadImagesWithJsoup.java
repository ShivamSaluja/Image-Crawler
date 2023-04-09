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
			doc = Jsoup.connect("http://www.xyz.com/").get();	// URL from where you want to collect photos

			Elements images = doc.select("*[src~=(?i)\\.(png|jpe?g|gif)]");	// Simple selector to spot all img tags in html Doc
			images.addAll(doc.select("*[background~=(.*)\\.(png|jpe?g|gif)]"));
			System.out.println("src : " + images.size());
			int count = 0;
			for (Element image : images) {
				System.out.println("count : " + count++);

				String imgURL = image.absUrl("src").isEmpty() ? image.absUrl("background") : image.absUrl("src");                    // Collecting data-original(or src) attribute of img tag (I have split it on ? so as to remove extra URL parameters such as fit=, crop=)
				new File("D:\\abc\\xyz\\images\\").mkdirs();                                             // Create new directory in you system where you want to store images

				System.out.println("imgURL  : " + imgURL );

				if (!imgURL.isEmpty()){
					URL url = new URL(imgURL);
					InputStream in = url.openConnection().getInputStream();
					Files.copy(in, Paths.get("D:\\abc\\xyz\\images\\" + imgURL.substring(imgURL.lastIndexOf("/"))), // Save image in created folder with some name
						StandardCopyOption.REPLACE_EXISTING);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}