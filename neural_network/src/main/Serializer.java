package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import neural_network.NeuralNetwork;
import neural_network.Neuron;
import neural_network.SimpleValue;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;


public class Serializer {


	public static void Serialize(String filename, Object o)
	{
		XStream xstream = getXStream();
		
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		xstream.toXML(o, os);
		
		try {
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Object DeSerialize(String filename)
	{
		XStream xstream = getXStream();
		
		FileInputStream is = null;
		try {
			is = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Object o = xstream.fromXML(is);

		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return o;
	}
	
	
	private static XStream getXStream()
	{
		XStream xstream = new XStream(new JettisonMappedXmlDriver());
		xstream.setMode(XStream.NO_REFERENCES);
		
		xstream.alias("nn", NeuralNetwork.class);
		xstream.aliasField("ii", NeuralNetwork.class, "inputs");
		xstream.aliasField("oo", NeuralNetwork.class, "output");
		xstream.aliasField("nnn", NeuralNetwork.class, "neurons");
		
		xstream.alias("q", SimpleValue.class);
		xstream.aliasField("", SimpleValue.class, "value");
		
		xstream.alias("n", Neuron.class);
		xstream.aliasField("e", Neuron.class, "deltaWeights");
		xstream.aliasField("w", Neuron.class, "weights");
		xstream.aliasField("i", Neuron.class, "inputs");
		xstream.aliasField("f", Neuron.class, "delta");
		xstream.aliasField("o", Neuron.class, "output");
		
		xstream.alias("d", Double.TYPE);
		
		return xstream;
	}
	
}
