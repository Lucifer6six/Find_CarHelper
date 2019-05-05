package com.upbest.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Handler;
import android.view.KeyEvent;

/**
 * <pre>
 * 文件名：	AlertDialogUtil
 * 作　者：	lijianqiang
 * 描　述：	AlertDialog工具类
 * @author
 * </pre>
 */
public class AlertDialogUtil {

    private static AlertDialogUtil instance;

    public static AlertDialogUtil getInstance() {
        if (instance == null) {
            instance = new AlertDialogUtil();
        }
        return instance;
    }

    /**
     * showAlertDialg
     *
     * @param context
     * @param msg
     * @param msgHandler
     */
    @SuppressWarnings("deprecation")
    public void showAlertDialog(Context context, String msg,
                               final Handler msgHandler) {
        try {
            AlertDialog dialog = new AlertDialog.Builder(context).create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnKeyListener(new OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface dialog, int keyCode,
                                     KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK
                            || keyCode == KeyEvent.KEYCODE_SEARCH
                            || keyCode == KeyEvent.KEYCODE_MENU) {
                        return true;
                    }
                    return false;
                }
            });
            dialog.setTitle("温馨提示");
            dialog.setMessage(msg);
            dialog.setButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (msgHandler != null) {
                                msgHandler.sendEmptyMessage(0);
                            }
                        }
                    });
            dialog.setButton2("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (msgHandler != null) {
                                msgHandler.sendEmptyMessage(1);
                            }
                        }
                    });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param msg
     * @param title
     * @param sure
     * @param cancel
     * @param msgHandler
     */
    @SuppressWarnings("deprecation")
    public void showAlertDialog(Context context, String msg, String title,
                               String sure, String cancel, final Handler msgHandler) {
        try {
            AlertDialog dialog = new AlertDialog.Builder(context).create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnKeyListener(new OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface dialog, int keyCode,
                                     KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK
                            || keyCode == KeyEvent.KEYCODE_SEARCH
                            || keyCode == KeyEvent.KEYCODE_MENU) {
                        return true;
                    }
                    return false;
                }
            });
            dialog.setTitle(title);
            dialog.setMessage(msg);
            dialog.setButton(cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (msgHandler != null) {
                        msgHandler.sendEmptyMessage(0);
                    }
                }
            });
            dialog.setButton2(sure, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (msgHandler != null) {
                        msgHandler.sendEmptyMessage(1);
                    }
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param msg
     * @param title
     * @param sure
     * @param cancel
     * @param cancelAble TODO
     * @param msgHandler
     */
    @SuppressWarnings("deprecation")
    public void showAlertDialog(Context context, String msg, String title,
                               String sure, String cancel, boolean cancelAble,
                               final Handler msgHandler) {
        try {
            AlertDialog dialog = new AlertDialog.Builder(context).create();
            dialog.setCanceledOnTouchOutside(false);
            if (!cancelAble) {
                dialog.setOnKeyListener(new OnKeyListener() {

                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode,
                                         KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK
                                || keyCode == KeyEvent.KEYCODE_SEARCH
                                || keyCode == KeyEvent.KEYCODE_MENU) {
                            return true;
                        }
                        return false;
                    }
                });
            }
            dialog.setTitle(title);
            dialog.setMessage(msg);
            dialog.setButton(cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (msgHandler != null) {
                        msgHandler.sendEmptyMessage(0);
                    }
                }
            });
            dialog.setButton2(sure, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (msgHandler != null) {
                        msgHandler.sendEmptyMessage(1);
                    }
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 只有一个按钮的dialog
     *
     * @param context
     * @param msg
     * @param msgHandler
     */
    @SuppressWarnings("deprecation")
    public void showSingleAlertDialog(Context context, String msg,
                                     final Handler msgHandler) {
        try {
            AlertDialog dialog = new AlertDialog.Builder(context).create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnKeyListener(new OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface dialog, int keyCode,
                                     KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK
                            || keyCode == KeyEvent.KEYCODE_SEARCH
                            || keyCode == KeyEvent.KEYCODE_MENU) {
                        return true;
                    }
                    return false;
                }
            });
            dialog.setTitle("温馨提示");
            dialog.setMessage(msg);
            dialog.setButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (msgHandler != null) {
                                dialog.dismiss();
                                msgHandler.sendEmptyMessage(1);
                            }
                        }
                    });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 只有一个按钮的dialog
     *
     * @param context
     * @param msg
     * @param title
     * @param sure
     * @param msgHandler
     */
    @SuppressWarnings("deprecation")
    public void showSingleAlertDialog(Context context, String msg, String title,
                                     String sure, final Handler msgHandler) {
        try {
            AlertDialog dialog = new AlertDialog.Builder(context).create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnKeyListener(new OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface dialog, int keyCode,
                                     KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK
                            || keyCode == KeyEvent.KEYCODE_SEARCH
                            || keyCode == KeyEvent.KEYCODE_MENU) {
                        return true;
                    }
                    return false;
                }
            });
            dialog.setTitle(title);
            dialog.setMessage(msg);
            dialog.setButton(sure, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (msgHandler != null) {
                        dialog.dismiss();
                        msgHandler.sendEmptyMessage(1);
                    }
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 只有一个按钮的dialog
     *
     * @param context
     * @param msg
     * @param title
     * @param sure
     * @param msgHandler
     */
    @SuppressWarnings("deprecation")
    public void showSingleAlertDialog(Context context, String msg, String title,
                                     String sure, boolean cancelAble, final Handler msgHandler) {
        try {
            AlertDialog dialog = new AlertDialog.Builder(context).create();
            dialog.setCanceledOnTouchOutside(false);
            if (!cancelAble) {
                dialog.setOnKeyListener(new OnKeyListener() {

                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode,
                                         KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK
                                || keyCode == KeyEvent.KEYCODE_SEARCH
                                || keyCode == KeyEvent.KEYCODE_MENU) {
                            return true;
                        }
                        return false;
                    }
                });
            }
            if(title!=null) {
                dialog.setTitle(title);
            }
            dialog.setMessage(msg);
            dialog.setButton(sure, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (msgHandler != null) {
                        msgHandler.sendEmptyMessage(1);
                    }
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
