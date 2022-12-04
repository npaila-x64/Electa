package vistas.admin;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Da formato a una fecha.
 */
public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    /**
     * Indica el formato o patrón de una fecha.
     */
    private String datePattern = "dd-MM-yyyy";
    /**
     * Instancia un objeto de tipo SimpleDateFormat indicando un patrón para la fecha.
     */
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    /**
     * Parsea texto retornando un objeto arbitrario.
     *
     * @param text el String a convertir.
     * @return un objeto de tipo Object.
     * @throws ParseException cuando ocurre un error al intentar parsear el texto dado.
     */
    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    /**
     * Devuelve el valor de String que se va a mostrar como valor.
     *
     * @param value el valor a convertir.
     * @return un String del valor convertido.
     * @throws ParseException cuando ocurre un error al intentar parsear el valor dado.
     */
    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

}