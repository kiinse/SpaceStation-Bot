package kiinse.spacestation.bot.bot.factories;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KeyboardsFactory {

    public ReplyKeyboardMarkup Keyboard(String row1, String row2, String row3){
        var replyKeyboardMarkup = new ReplyKeyboardMarkup();
        var keyboard = new ArrayList<KeyboardRow>();
        var keyboardFirstRow = new KeyboardRow();
        var keyboardSecondRow = new KeyboardRow();
        var keyboardThirdRow = new KeyboardRow();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        keyboardFirstRow.add(row1);
        keyboardSecondRow.add(row2);
        keyboardThirdRow.add(row3);
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup inlineConstructor(String button1, String callback1, String button2, String callback2) {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        var inlineKeyboardButton1 = new InlineKeyboardButton();
        var inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText(button1);
        inlineKeyboardButton1.setCallbackData(callback1);
        inlineKeyboardButton2.setText(button2);
        inlineKeyboardButton2.setCallbackData(callback2);
        var keyboardButtonsRow1 = new ArrayList<InlineKeyboardButton> ();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);
        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardButtonsRow1));
        return inlineKeyboardMarkup;
    }

    @SuppressWarnings("DuplicatedCode")
    public InlineKeyboardMarkup settings() {
        var rows = new ArrayList<List<InlineKeyboardButton>>();
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        var inlineKeyboardButton1 = new InlineKeyboardButton();
        var inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Изменить логин🆔");
        inlineKeyboardButton1.setCallbackData("Изменить логин");

        inlineKeyboardButton2.setText("Закончить");
        inlineKeyboardButton2.setCallbackData("Завершить");

        var keyboardButtonsRow1 = new ArrayList<InlineKeyboardButton> ();
        var keyboardButtonsRow2 = new ArrayList<InlineKeyboardButton> ();

        keyboardButtonsRow1.add(inlineKeyboardButton1);
        rows.add(keyboardButtonsRow1);
        keyboardButtonsRow2.add(inlineKeyboardButton2);
        rows.add(keyboardButtonsRow2);


        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }
}
