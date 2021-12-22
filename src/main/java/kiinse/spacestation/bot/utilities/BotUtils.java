package kiinse.spacestation.bot.utilities;

import kiinse.spacestation.api.external.MySQLUtils;
import kiinse.spacestation.bot.bot.Bot;
import kiinse.spacestation.bot.utilities.antispam.AntiSpam;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class BotUtils {

    private static final Bot bot = new Bot();

    /**
     * Enum видов рассылок
     */
    public enum Mailing {
        AUTOMAILING,
        NEXTLESSONMAILING,
        CHANGESNOTICE
    }

    public void sendMessage(String chat, String text, ReplyKeyboardMarkup replyKeyBoard, InlineKeyboardMarkup inlineKeyboard) {
        var message = new SendMessage();
        message.setChatId(chat);
        message.enableHtml(true);
        if (text.length() >= 4096) {
            message.setText(text.substring(0, 2048));
            execute(chat, text, message);
            message.setText(text.substring(2048));
            if (inlineKeyboard != null) {
                message.setReplyMarkup(inlineKeyboard);
            } else if (replyKeyBoard != null) {
                message.setReplyMarkup(replyKeyBoard);
            }
            execute(chat, text, message);
        } else {
            message.setText(text);
            if (inlineKeyboard != null) {
                message.setReplyMarkup(inlineKeyboard);
            } else if (replyKeyBoard != null) {
                message.setReplyMarkup(replyKeyBoard);
            }
            execute(chat, text, message);
        }
    }

    public void editMessageText(String chat, int message_id, String text, InlineKeyboardMarkup inlineKeyboard) {
        var message = new EditMessageText();
        message.setChatId(chat);
        message.setMessageId(message_id);
        message.enableHtml(true);
        message.setText(text);
        if (inlineKeyboard != null) {
            message.setReplyMarkup(inlineKeyboard);
        }
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            log.warn("Ошибка при редактировании сообщения.\nID чата: {} | ID сообщения: {} | Причина: {} |\nТекст: {}", chat, message_id, e.getMessage(), text);
        }
    }

    private void execute(String chat, String text, SendMessage message) {
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            if (e.getMessage().contains("was blocked by the user") || e.getMessage().contains("user is deactivated")) {
                MySQLUtils.updateUserName(chat, "БОТ ЗАБЛОКИРОВАН");
                log.warn("Бот заблокирован. ID чата: {}", chat);
            } else {
                log.warn("Ошибка при отправке сообщения.\nID чата: {} | Группа пользователя: {} | Причина: {} |\nТекст: {}", chat, MySQLUtils.getLogin(chat), e.getMessage(), text);
            }
        }
    }
}
