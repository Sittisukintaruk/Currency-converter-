package sample;


import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Object;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.Currency;
import java.util.Iterator;


public class Controller {

    public TextField Textfull,Textfull01;
    public ComboBox combobox01,combobox02;
    public Label label1 , label2;
    public double Number,text_double,Sum = 0.0 ;
    String Symbol ,json,base;
    DecimalFormat df = new DecimalFormat("#.##");

    //SET DATA Before Stats Programme
    public void setdata(){
        try {
            json = Getjson("THB");
            Parsejson(json);
            configureTextFieldToAcceptOnlyDecimalValues(Textfull);
            configureTextFieldToAcceptOnlyDecimalValues(Textfull01);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //GET JSON FROM URL
    public String Getjson(String base) throws  Exception {
        String url = "https://api.exchangeratesapi.io/latest?base="+base;
        URL obj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responsecode = conn.getResponseCode();
        if (responsecode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        } else {
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String input;
        StringBuffer respose = new StringBuffer();
        while ((input = in.readLine()) != null) {
            respose.append(input);
        }
        in.close();
        return respose.toString();
    }




// SET DATA ON COMBOBOX With json
    public void Parsejson(String json) throws Exception {
        JSONObject o = new JSONObject(json);
        JSONObject rate = o.getJSONObject("rates");
        Iterator<String> keys = rate.keys();

        while(keys.hasNext()) {
            String key = keys.next();
            combobox01.getItems().add(key);
            combobox02.getItems().add(key);

            if (rate.get(key) instanceof JSONObject) {
                // do something with jsonObject here
            }
        }
    }
    // SET Selection Combobox
    public void getdata() {
        combobox01.getSelectionModel().selectFirst();
        combobox02.getSelectionModel().selectFirst();
    }
    // GET NEW BASE NUMBER
    public void getbase(String bases) throws Exception {
        base = Getjson(bases);
        Number = getjson(base);
    }
    // GET DATA FROM KEY DATA
    private Double getjson(String base){
        JSONObject o = new JSONObject(base);
        JSONObject rate = o.getJSONObject("rates");
        return rate.getDouble(combobox02.getValue().toString());
    }
    // combobox action event handler
    public void  loopaddcombobox(ActionEvent actionEvent) throws Exception {
        Currency currency = Currency.getInstance(combobox01.getValue().toString());
        Symbol = currency.getSymbol();
        label1.setText(Symbol);
        checkcombobox();

    }
    // SET TextField only Double
    public static void configureTextFieldToAcceptOnlyDecimalValues(TextField textField) {

        DecimalFormat format = new DecimalFormat("#");

        final TextFormatter<Object> decimalTextFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().isEmpty()) {
                return change;
            }
            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(change.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < change.getControlNewText().length()) {
                return null;
            } else {
                return change;
            }
        });
        textField.setTextFormatter(decimalTextFormatter);
    }
    //  TextField action event handler
    public void copy1(KeyEvent keyEvent) throws Exception {
        String text =  Textfull.getText();
        getbase(combobox01.getValue().toString());
        if(!(text.isEmpty())) {
             text_double = Double.parseDouble(text);
             Sum = text_double * Number;
             Textfull01.setText(df.format(Sum));
        }
        else {
            Textfull01.setText("");
        }
    }
    //  TextField action event handler
    public void copy2(KeyEvent keyEvent)
    {
        String text = Textfull01.getText();
        if(!(text.isEmpty())){
            text_double = Double.parseDouble(text);
            Sum = text_double / Number;
            Textfull.setText(df.format(Sum));
        }
        else {
            Textfull.setText("");
        }
    }

    // combobox action event handler
    public void loopaddcombobox1(ActionEvent actionEvent) throws Exception {
        Currency currency = Currency.getInstance(combobox02.getValue().toString());
        Symbol = currency.getSymbol();
        label2.setText(Symbol);
        checkcombobox();
    }
    // Button action event handler

    public void Chooshe(MouseEvent mouseEvent) {
        int getcombo = combobox01.getSelectionModel().getSelectedIndex();
        combobox01.getSelectionModel().select(combobox02.getSelectionModel().getSelectedIndex());
        combobox02.getSelectionModel().select(getcombo);
    }
    // CheckCombobox is Empty
    public void checkcombobox() throws Exception {
        if(!(Textfull.getText().isEmpty() && Textfull01.getText().isEmpty()))
        {
            getbase(combobox01.getValue().toString());
            String text =  Textfull.getText();
            text_double = Double.parseDouble(text);
            Sum = text_double * Number;
            Textfull01.setText(df.format(Sum));
        }
    }
}
