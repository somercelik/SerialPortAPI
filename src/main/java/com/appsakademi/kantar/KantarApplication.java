package com.appsakademi.kantar;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

@SpringBootApplication
public class KantarApplication {
	static String brut, dara, net, urun_kodu, islem_no, date_time;
	static SerialPort[] allPorts;
	static SerialPort serialPort;
	static final String urlStr = "http://127.0.0.1:8080/kantar/";
	static String jsonInputStr;
	static HttpURLConnection con = null;
	static URL url = null;

	public static void main(String[] args) {
		SpringApplication.run(KantarApplication.class, args);

		initSerialPort();

		InputStream in = serialPort.getInputStream();			//Artık in değişkeni üzerinden veri okuyabileceğiz.
		final String[] temp = {new String()};
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		serialPort.addDataListener(new SerialPortDataListener() {
			@Override
			public int getListeningEvents() {
				return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
			}

			@Override
			public void serialEvent(SerialPortEvent event) {
				for (int i = 0; i < 38; ++i){					//SABİT 38 bayt olduğu düşünülüyor.
					try {
						temp[0] += String.valueOf((char)in.read());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				date_time = formatter.format(System.currentTimeMillis());
				brut = temp[0].split("\\:")[0].trim();
				dara = temp[0].split("\\:")[1].trim();
				net = temp[0].split("\\:")[2].trim();
				urun_kodu = temp[0].split("\\:")[3].trim();
				islem_no = temp[0].split("\\:")[4].trim();

				try {
					postInternal(date_time, brut, dara, net, urun_kodu, islem_no);
				} catch (IOException e) {
					e.printStackTrace();
				}
				temp[0] = ""; brut = ""; dara = "";net = "";urun_kodu = "";islem_no = "";
			}
		});

	}

	//LOCALHOST'a POST işlemi yapan fonksiyon
	private static void postInternal(String date_time, String brut, String dara, String net, String urun_kodu, String islem_no) throws IOException {
		jsonInputStr = "{ \"date_time\": \"" + date_time + "\",\"brut\": \"" + brut + "\", \"dara\": \"" + dara + "\", \"net\": \"" + net + "\", \"urun_kodu\": \"" + urun_kodu + "\",\"islem_no\": \"" + islem_no + "\" }";

		url = new URL(urlStr);
		con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);

		OutputStream os = con.getOutputStream();
		byte[] input = jsonInputStr.getBytes(StandardCharsets.UTF_8);
		os.write(input, 0, input.length);

		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
		con.disconnect();
	}

	private static void initSerialPort() {
		allPorts = SerialPort.getCommPorts();
		int chosenPort = 1;
		System.out.println(allPorts[chosenPort - 1].getDescriptivePortName()+" - "+ allPorts[chosenPort - 1].getSystemPortName()+" secildi.");
		serialPort = allPorts[chosenPort - 1];

		if (serialPort.openPort())
			System.out.println("Baglanti basarili!");
		else{
			System.out.println("Baglanti basarisiz!");
		}

		serialPort.setBaudRate(9600);							//Port iletişim kuralları burada set edilir.
		serialPort.setNumDataBits(8);
		serialPort.setNumStopBits(1);
		serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
	}

}
