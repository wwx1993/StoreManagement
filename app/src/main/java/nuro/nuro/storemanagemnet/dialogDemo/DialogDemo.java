package nuro.nuro.storemanagemnet.dialogDemo;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by Administrator on 2018/3/29.
 */
public class DialogDemo {
    // 错误消息对话框
    public static void builder(Context context, String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("确定", null);
        builder.create();
        builder.show();
    }
}
