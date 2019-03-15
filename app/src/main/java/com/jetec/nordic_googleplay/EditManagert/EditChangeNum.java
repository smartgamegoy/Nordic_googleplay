package com.jetec.nordic_googleplay.EditManagert;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.jetec.nordic_googleplay.Value;

public class EditChangeNum implements TextWatcher {

    private String TAG = "EditChangeNum";
    private EditText editText;
    private String name;
    private boolean last;

    public EditChangeNum(EditText editText, String name) {
        this.editText = editText;
        this.name = name;
        Log.e(TAG, "name = " + name);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void afterTextChanged(Editable editable) {
        String num = editText.getText().toString().trim();
        Log.e(TAG, "num = " + num);
        if (name.matches("T")) {
            if (!num.equals("-") && !num.equals("")) {
                if (num.equals(".")) {
                    last = true;
                    editText.setText("0.");
                } else if (num.matches("-\\.") && !num.matches("-0")) {
                    last = true;
                    if (num.equals("-."))
                        editText.setText("-0.");
                } else {
                    if (Float.valueOf(num) > 65.0) {
                        last = true;
                        editText.setText("65");
                    } else if (Float.valueOf(num) < -10.0) {
                        last = true;
                        editText.setText("-10");
                    } else {
                        if (Float.valueOf(num) > 0) {
                            int len = 3;
                            byte[] bytes = String.valueOf(editable).getBytes();
                            if (num.startsWith("0") && !num.startsWith("0.")) {
                                last = true;
                                num = num.replaceFirst("^0*", "");
                                editText.setText(num);
                            }
                            if (last) {
                                last = false;
                                int selEndIndex = editText.getText().length();
                                Selection.setSelection(editable, selEndIndex);
                            }
                            if (bytes.length > len) {
                                last = true;
                                byte[] newBytes = new byte[len];
                                System.arraycopy(bytes, 0, newBytes, 0, len);
                                String newStr = new String(newBytes);
                                editText.setText(newStr);
                            } else {
                                int index = editText.getSelectionStart();
                                Selection.setSelection(editable, index);
                            }
                        } else if (Float.valueOf(num) == 0 && !num.equals("0.") && !num.equals("-0.") && !num.equals("-0")) {
                            int len = 1;
                            byte[] bytes = String.valueOf(editable).getBytes();
                            if (last) {
                                last = false;
                                int selEndIndex = editText.getText().length();
                                Selection.setSelection(editable, selEndIndex);
                            }
                            if (bytes.length > len) {
                                last = true;
                                byte[] newBytes = new byte[len];
                                if (!num.startsWith("-")) {
                                    System.arraycopy(bytes, 0, newBytes, 0, len);
                                    String newStr = new String(newBytes);
                                    editText.setText(newStr);
                                } else {
                                    editText.setText("-0");
                                }
                            } else {
                                if (!num.startsWith("-")) {
                                    int index = editText.getSelectionStart();
                                    Selection.setSelection(editable, index);
                                }
                            }
                        } else {
                            int len = 4;
                            byte[] bytes = String.valueOf(editable).getBytes();
                            if (num.startsWith("-00") && !num.startsWith("0.")) {
                                last = true;
                                num = num.substring(0, 2) + num.substring(2).replaceFirst("^0*", "");
                                editText.setText(num);
                            } else if (num.matches("-0.") && !num.matches("-0\\.")) {
                                last = true;
                                num = num.substring(0, 1) + num.substring(2).replaceFirst("^0*", "");
                                editText.setText(num);
                            }
                            if (bytes.length > len) {
                                last = true;
                                byte[] newBytes = new byte[len];
                                System.arraycopy(bytes, 0, newBytes, 0, len);
                                String newStr = new String(newBytes);
                                editText.setText(newStr);
                            } else {
                                if (last) {
                                    last = false;
                                    int selEndIndex = editText.getText().length();
                                    Selection.setSelection(editable, selEndIndex);
                                }
                                int index = editText.getSelectionStart();
                                Selection.setSelection(editable, index);
                            }
                        }
                    }
                }
            }
        } else if (name.matches("H")) {
            if (!num.matches("-") && !num.matches("")) {
                if (Integer.valueOf(num) > 100) {
                    last = true;
                    editText.setText("100");
                } else if (Integer.valueOf(num) < 0) {
                    last = true;
                    editText.setText("0");
                } else {
                    if (Float.valueOf(num) == 0) {
                        int len = 1;
                        byte[] bytes = String.valueOf(editable).getBytes();
                        if (last) {
                            last = false;
                            int selEndIndex = editText.getText().length();
                            Selection.setSelection(editable, selEndIndex);
                        }
                        if (bytes.length > len) {
                            last = true;
                            byte[] newBytes = new byte[len];
                            System.arraycopy(bytes, 0, newBytes, 0, len);
                            String newStr = new String(newBytes);
                            editText.setText(newStr);
                        } else {
                            int index = editText.getSelectionStart();
                            Selection.setSelection(editable, index);
                        }
                    } else {
                        if (num.startsWith("0")) {
                            last = true;
                            num = num.replaceFirst("^0*", "");
                            editText.setText(num);
                        }
                    }
                    if (last) {
                        last = false;
                        int selEndIndex = editText.getText().length();
                        Selection.setSelection(editable, selEndIndex);
                    } else {
                        int index = editText.getSelectionStart();
                        Selection.setSelection(editable, index);
                    }
                }
            }
        } else if (name.matches("C")) {
            if (!num.matches("-") && !num.matches("")) {
                if (Integer.valueOf(num) > 2000) {
                    last = true;
                    editText.setText("2000");
                } else if (Integer.valueOf(num) < 0) {
                    last = true;
                    editText.setText("0");
                } else {
                    if (Float.valueOf(num) == 0) {
                        int len = 1;
                        byte[] bytes = String.valueOf(editable).getBytes();
                        if (last) {
                            last = false;
                            int selEndIndex = editText.getText().length();
                            Selection.setSelection(editable, selEndIndex);
                        }
                        if (bytes.length > len) {
                            last = true;
                            byte[] newBytes = new byte[len];
                            System.arraycopy(bytes, 0, newBytes, 0, len);
                            String newStr = new String(newBytes);
                            editText.setText(newStr);
                        } else {
                            int index = editText.getSelectionStart();
                            Selection.setSelection(editable, index);
                        }
                    } else {
                        if (num.startsWith("0")) {
                            last = true;
                            num = num.replaceFirst("^0*", "");
                            editText.setText(num);
                        }
                    }
                    if (last) {
                        last = false;
                        int selEndIndex = editText.getText().length();
                        Selection.setSelection(editable, selEndIndex);
                    } else {
                        int index = editText.getSelectionStart();
                        Selection.setSelection(editable, index);
                    }
                }
            }
        } else if (name.matches("D")) {
            if (!num.matches("-") && !num.matches("")) {
                if (Integer.valueOf(num) > 3000) {
                    last = true;
                    editText.setText("3000");
                } else if (Integer.valueOf(num) < 0) {
                    last = true;
                    editText.setText("0");
                } else {
                    if (Float.valueOf(num) == 0) {
                        int len = 1;
                        byte[] bytes = String.valueOf(editable).getBytes();
                        if (last) {
                            last = false;
                            int selEndIndex = editText.getText().length();
                            Selection.setSelection(editable, selEndIndex);
                        }
                        if (bytes.length > len) {
                            last = true;
                            byte[] newBytes = new byte[len];
                            System.arraycopy(bytes, 0, newBytes, 0, len);
                            String newStr = new String(newBytes);
                            editText.setText(newStr);
                        } else {
                            int index = editText.getSelectionStart();
                            Selection.setSelection(editable, index);
                        }
                    } else {
                        if (num.startsWith("0")) {
                            last = true;
                            num = num.replaceFirst("^0*", "");
                            editText.setText(num);
                        }
                    }
                    if (last) {
                        last = false;
                        int selEndIndex = editText.getText().length();
                        Selection.setSelection(editable, selEndIndex);
                    } else {
                        int index = editText.getSelectionStart();
                        Selection.setSelection(editable, index);
                    }
                }
            }
        } else if (name.matches("E")) {
            if (!num.matches("-") && !num.matches("")) {
                if (Integer.valueOf(num) > 5000) {
                    last = true;
                    editText.setText("5000");
                } else if (Integer.valueOf(num) < 0) {
                    last = true;
                    editText.setText("0");
                } else {
                    if (Float.valueOf(num) == 0) {
                        int len = 1;
                        byte[] bytes = String.valueOf(editable).getBytes();
                        if (last) {
                            last = false;
                            int selEndIndex = editText.getText().length();
                            Selection.setSelection(editable, selEndIndex);
                        }
                        if (bytes.length > len) {
                            last = true;
                            byte[] newBytes = new byte[len];
                            System.arraycopy(bytes, 0, newBytes, 0, len);
                            String newStr = new String(newBytes);
                            editText.setText(newStr);
                        } else {
                            int index = editText.getSelectionStart();
                            Selection.setSelection(editable, index);
                        }
                    } else {
                        if (num.startsWith("0")) {
                            last = true;
                            num = num.replaceFirst("^0*", "");
                            editText.setText(num);
                        }
                    }
                    if (last) {
                        last = false;
                        int selEndIndex = editText.getText().length();
                        Selection.setSelection(editable, selEndIndex);
                    } else {
                        int index = editText.getSelectionStart();
                        Selection.setSelection(editable, index);
                    }
                }
            }
        } else if (name.matches("ADR")) {
            if (!num.matches("-") && !num.matches("")) {
                if (Integer.valueOf(num) > 255) {
                    last = true;
                    editText.setText("255");
                } else if (Integer.valueOf(num) < 1) {
                    last = true;
                    editText.setText("1");
                } else {
                    if (Float.valueOf(num) == 0) {
                        int len = 1;
                        byte[] bytes = String.valueOf(editable).getBytes();
                        if (last) {
                            last = false;
                            int selEndIndex = editText.getText().length();
                            Selection.setSelection(editable, selEndIndex);
                        }
                        if (bytes.length > len) {
                            last = true;
                            byte[] newBytes = new byte[len];
                            System.arraycopy(bytes, 0, newBytes, 0, len);
                            String newStr = new String(newBytes);
                            editText.setText(newStr);
                        } else {
                            int index = editText.getSelectionStart();
                            Selection.setSelection(editable, index);
                        }
                    } else {
                        if (num.startsWith("0")) {
                            last = true;
                            num = num.replaceFirst("^0*", "");
                            editText.setText(num);
                        }
                    }
                    if (last) {
                        last = false;
                        int selEndIndex = editText.getText().length();
                        Selection.setSelection(editable, selEndIndex);
                    } else {
                        int index = editText.getSelectionStart();
                        Selection.setSelection(editable, index);
                    }
                }
            }
        }else if (name.matches("I1")) {
            if (!Value.IDP1) {
                if (!num.matches("-") && !num.matches("")) {
                    if (Integer.valueOf(num) > 9999) {
                        last = true;
                        editText.setText("9999");
                    } else if (Integer.valueOf(num) < -999) {
                        last = true;
                        editText.setText("-999");
                    }else {
                        if (Float.valueOf(num) == 0) {
                            int len = 1;
                            byte[] bytes = String.valueOf(editable).getBytes();
                            if (last) {
                                last = false;
                                int selEndIndex = editText.getText().length();
                                Selection.setSelection(editable, selEndIndex);
                            }
                            if (bytes.length > len) {
                                last = true;
                                byte[] newBytes = new byte[len];
                                if(!num.startsWith("-")) {
                                    System.arraycopy(bytes, 0, newBytes, 0, len);
                                    String newStr = new String(newBytes);
                                    editText.setText(newStr);
                                }
                                else {
                                    if(!num.matches("-0"))
                                        editText.setText("-0");
                                }
                            } else {
                                int index = editText.getSelectionStart();
                                Selection.setSelection(editable, index);
                            }
                        } else {
                            if(num.startsWith("0")) {
                                last = true;
                                num = num.replaceFirst("^0*", "");
                                editText.setText(num);
                            }
                            else {
                                if(num.startsWith("-0")) {
                                    last = true;
                                    num = num.substring(0, 1) + num.substring(2).replaceFirst("^0*", "");
                                    editText.setText(num);
                                }
                            }
                        }
                        if (last) {
                            last = false;
                            int selEndIndex = editText.getText().length();
                            Selection.setSelection(editable, selEndIndex);
                        } else {
                            int index = editText.getSelectionStart();
                            Selection.setSelection(editable, index);
                        }
                    }
                }
            }else {
                if (!num.equals("-") && !num.equals("")) {
                    if (num.equals(".")) {
                        last = true;
                        editText.setText("0.");
                    } else if (num.matches("-\\.") && !num.matches("-0")) {
                        last = true;
                        if (num.equals("-."))
                            editText.setText("-0.");
                    }else {
                        if (Float.valueOf(num) * 10 > 9999) {
                            last = true;
                            editText.setText("999.9");
                        } else if (Float.valueOf(num) * 10 < -1999) {
                            last = true;
                            editText.setText("-199.9");
                        } else {
                            if (Float.valueOf(num) > 0) {
                                int len = 5;
                                byte[] bytes = String.valueOf(editable).getBytes();
                                if (num.startsWith("0") && !num.startsWith("0.")) {
                                    last = true;
                                    num = num.replaceFirst("^0*", "");
                                    editText.setText(num);
                                }
                                if (last) {
                                    last = false;
                                    int selEndIndex = editText.getText().length();
                                    Selection.setSelection(editable, selEndIndex);
                                }
                                String[] arr = num.split("\\.");
                                if(arr.length == 2){
                                    if(arr[1].length() > 1){
                                        last = true;
                                        String reset = String.valueOf(arr[1].charAt(0));
                                        num = arr[0] + "." + reset;
                                        editText.setText(num);
                                    }
                                }
                                if (bytes.length > len) {
                                    last = true;
                                    byte[] newBytes = new byte[len];
                                    System.arraycopy(bytes, 0, newBytes, 0, len);
                                    String newStr = new String(newBytes);
                                    editText.setText(newStr);
                                } else {
                                    int index = editText.getSelectionStart();
                                    Selection.setSelection(editable, index);
                                }
                            } else if (Float.valueOf(num) == 0 && !num.equals("0.") && !num.equals("-0.") && !num.equals("-0")) {
                                int len = 1;
                                byte[] bytes = String.valueOf(editable).getBytes();
                                if (last) {
                                    last = false;
                                    int selEndIndex = editText.getText().length();
                                    Selection.setSelection(editable, selEndIndex);
                                }
                                if (bytes.length > len) {
                                    last = true;
                                    byte[] newBytes = new byte[len];
                                    if (!num.startsWith("-")) {
                                        System.arraycopy(bytes, 0, newBytes, 0, len);
                                        String newStr = new String(newBytes);
                                        editText.setText(newStr);
                                    } else {
                                        editText.setText("-0");
                                    }
                                } else {
                                    if (!num.startsWith("-")) {
                                        int index = editText.getSelectionStart();
                                        Selection.setSelection(editable, index);
                                    }
                                }
                            } else {
                                int len = 6;
                                byte[] bytes = String.valueOf(editable).getBytes();
                                if (num.startsWith("-00") && !num.startsWith("0.")) {
                                    last = true;
                                    num = num.substring(0, 2) + num.substring(2).replaceFirst("^0*", "");
                                    editText.setText(num);
                                } else if (num.matches("-0.") && !num.matches("-0\\.")) {
                                    last = true;
                                    num = num.substring(0, 1) + num.substring(2).replaceFirst("^0*", "");
                                    editText.setText(num);
                                }
                                String[] arr = num.split("\\.");
                                if(arr.length == 2){
                                    if(arr[1].length() > 1){
                                        last = true;
                                        String reset = String.valueOf(arr[1].charAt(0));
                                        num = arr[0] + "." + reset;
                                        editText.setText(num);
                                    }
                                }
                                if (bytes.length > len) {
                                    last = true;
                                    byte[] newBytes = new byte[len];
                                    System.arraycopy(bytes, 0, newBytes, 0, len);
                                    String newStr = new String(newBytes);
                                    editText.setText(newStr);
                                } else {
                                    if (last) {
                                        last = false;
                                        int selEndIndex = editText.getText().length();
                                        Selection.setSelection(editable, selEndIndex);
                                    }
                                    int index = editText.getSelectionStart();
                                    Selection.setSelection(editable, index);
                                }
                            }
                        }
                    }
                }
            }
        }else if (name.matches("I2")) {
            if (!Value.IDP2) {
                if (!num.matches("-") && !num.matches("")) {
                    if (Integer.valueOf(num) > 9999) {
                        last = true;
                        editText.setText("9999");
                    } else if (Integer.valueOf(num) < -999) {
                        last = true;
                        editText.setText("-999");
                    }else {
                        if (Float.valueOf(num) == 0) {
                            int len = 1;
                            byte[] bytes = String.valueOf(editable).getBytes();
                            if (last) {
                                last = false;
                                int selEndIndex = editText.getText().length();
                                Selection.setSelection(editable, selEndIndex);
                            }
                            if (bytes.length > len) {
                                last = true;
                                byte[] newBytes = new byte[len];
                                if(!num.startsWith("-")) {
                                    System.arraycopy(bytes, 0, newBytes, 0, len);
                                    String newStr = new String(newBytes);
                                    editText.setText(newStr);
                                }
                                else {
                                    if(!num.matches("-0"))
                                        editText.setText("-0");
                                }
                            } else {
                                int index = editText.getSelectionStart();
                                Selection.setSelection(editable, index);
                            }
                        } else {
                            if(num.startsWith("0")) {
                                last = true;
                                num = num.replaceFirst("^0*", "");
                                editText.setText(num);
                            }
                            else {
                                if(num.startsWith("-0")) {
                                    last = true;
                                    num = num.substring(0, 1) + num.substring(2).replaceFirst("^0*", "");
                                    editText.setText(num);
                                }
                            }
                        }
                        if (last) {
                            last = false;
                            int selEndIndex = editText.getText().length();
                            Selection.setSelection(editable, selEndIndex);
                        } else {
                            int index = editText.getSelectionStart();
                            Selection.setSelection(editable, index);
                        }
                    }
                }
            }else {
                if (!num.equals("-") && !num.equals("")) {
                    if (num.equals(".")) {
                        last = true;
                        editText.setText("0.");
                    } else if (num.matches("-\\.") && !num.matches("-0")) {
                        last = true;
                        if (num.equals("-."))
                            editText.setText("-0.");
                    }else {
                        if (Float.valueOf(num) * 10 > 9999) {
                            last = true;
                            editText.setText("999.9");
                        } else if (Float.valueOf(num) * 10 < -1999) {
                            last = true;
                            editText.setText("-199.9");
                        } else {
                            if (Float.valueOf(num) > 0) {
                                int len = 5;
                                byte[] bytes = String.valueOf(editable).getBytes();
                                if (num.startsWith("0") && !num.startsWith("0.")) {
                                    last = true;
                                    num = num.replaceFirst("^0*", "");
                                    editText.setText(num);
                                }
                                if (last) {
                                    last = false;
                                    int selEndIndex = editText.getText().length();
                                    Selection.setSelection(editable, selEndIndex);
                                }
                                String[] arr = num.split("\\.");
                                if(arr.length == 2){
                                    if(arr[1].length() > 1){
                                        last = true;
                                        String reset = String.valueOf(arr[1].charAt(0));
                                        num = arr[0] + "." + reset;
                                        editText.setText(num);
                                    }
                                }
                                if (bytes.length > len) {
                                    last = true;
                                    byte[] newBytes = new byte[len];
                                    System.arraycopy(bytes, 0, newBytes, 0, len);
                                    String newStr = new String(newBytes);
                                    editText.setText(newStr);
                                } else {
                                    int index = editText.getSelectionStart();
                                    Selection.setSelection(editable, index);
                                }
                            } else if (Float.valueOf(num) == 0 && !num.equals("0.") && !num.equals("-0.") && !num.equals("-0")) {
                                int len = 1;
                                byte[] bytes = String.valueOf(editable).getBytes();
                                if (last) {
                                    last = false;
                                    int selEndIndex = editText.getText().length();
                                    Selection.setSelection(editable, selEndIndex);
                                }
                                if (bytes.length > len) {
                                    last = true;
                                    byte[] newBytes = new byte[len];
                                    if (!num.startsWith("-")) {
                                        System.arraycopy(bytes, 0, newBytes, 0, len);
                                        String newStr = new String(newBytes);
                                        editText.setText(newStr);
                                    } else {
                                        editText.setText("-0");
                                    }
                                } else {
                                    if (!num.startsWith("-")) {
                                        int index = editText.getSelectionStart();
                                        Selection.setSelection(editable, index);
                                    }
                                }
                            } else {
                                int len = 6;
                                byte[] bytes = String.valueOf(editable).getBytes();
                                if (num.startsWith("-00") && !num.startsWith("0.")) {
                                    last = true;
                                    num = num.substring(0, 2) + num.substring(2).replaceFirst("^0*", "");
                                    editText.setText(num);
                                } else if (num.matches("-0.") && !num.matches("-0\\.")) {
                                    last = true;
                                    num = num.substring(0, 1) + num.substring(2).replaceFirst("^0*", "");
                                    editText.setText(num);
                                }
                                String[] arr = num.split("\\.");
                                if(arr.length == 2){
                                    if(arr[1].length() > 1){
                                        last = true;
                                        String reset = String.valueOf(arr[1].charAt(0));
                                        num = arr[0] + "." + reset;
                                        editText.setText(num);
                                    }
                                }
                                if (bytes.length > len) {
                                    last = true;
                                    byte[] newBytes = new byte[len];
                                    System.arraycopy(bytes, 0, newBytes, 0, len);
                                    String newStr = new String(newBytes);
                                    editText.setText(newStr);
                                } else {
                                    if (last) {
                                        last = false;
                                        int selEndIndex = editText.getText().length();
                                        Selection.setSelection(editable, selEndIndex);
                                    }
                                    int index = editText.getSelectionStart();
                                    Selection.setSelection(editable, index);
                                }
                            }
                        }
                    }
                }
            }
        }else if (name.matches("I3")) {
            if (!Value.IDP3) {
                if (!num.matches("-") && !num.matches("")) {
                    if (Integer.valueOf(num) > 9999) {
                        last = true;
                        editText.setText("9999");
                    } else if (Integer.valueOf(num) < -999) {
                        last = true;
                        editText.setText("-999");
                    }else {
                        if (Float.valueOf(num) == 0) {
                            int len = 1;
                            byte[] bytes = String.valueOf(editable).getBytes();
                            if (last) {
                                last = false;
                                int selEndIndex = editText.getText().length();
                                Selection.setSelection(editable, selEndIndex);
                            }
                            if (bytes.length > len) {
                                last = true;
                                byte[] newBytes = new byte[len];
                                if(!num.startsWith("-")) {
                                    System.arraycopy(bytes, 0, newBytes, 0, len);
                                    String newStr = new String(newBytes);
                                    editText.setText(newStr);
                                }
                                else {
                                    if(!num.matches("-0"))
                                        editText.setText("-0");
                                }
                            } else {
                                int index = editText.getSelectionStart();
                                Selection.setSelection(editable, index);
                            }
                        } else {
                            if(num.startsWith("0")) {
                                last = true;
                                num = num.replaceFirst("^0*", "");
                                editText.setText(num);
                            }
                            else {
                                if(num.startsWith("-0")) {
                                    last = true;
                                    num = num.substring(0, 1) + num.substring(2).replaceFirst("^0*", "");
                                    editText.setText(num);
                                }
                            }
                        }
                        if (last) {
                            last = false;
                            int selEndIndex = editText.getText().length();
                            Selection.setSelection(editable, selEndIndex);
                        } else {
                            int index = editText.getSelectionStart();
                            Selection.setSelection(editable, index);
                        }
                    }
                }
            }else {
                if (!num.equals("-") && !num.equals("")) {
                    if (num.equals(".")) {
                        last = true;
                        editText.setText("0.");
                    } else if (num.matches("-\\.") && !num.matches("-0")) {
                        last = true;
                        if (num.equals("-."))
                            editText.setText("-0.");
                    }else {
                        if (Float.valueOf(num) * 10 > 9999) {
                            last = true;
                            editText.setText("999.9");
                        } else if (Float.valueOf(num) * 10 < -1999) {
                            last = true;
                            editText.setText("-199.9");
                        } else {
                            if (Float.valueOf(num) > 0) {
                                int len = 5;
                                byte[] bytes = String.valueOf(editable).getBytes();
                                if (num.startsWith("0") && !num.startsWith("0.")) {
                                    last = true;
                                    num = num.replaceFirst("^0*", "");
                                    editText.setText(num);
                                }
                                if (last) {
                                    last = false;
                                    int selEndIndex = editText.getText().length();
                                    Selection.setSelection(editable, selEndIndex);
                                }
                                String[] arr = num.split("\\.");
                                if(arr.length == 2){
                                    if(arr[1].length() > 1){
                                        last = true;
                                        String reset = String.valueOf(arr[1].charAt(0));
                                        num = arr[0] + "." + reset;
                                        editText.setText(num);
                                    }
                                }
                                if (bytes.length > len) {
                                    last = true;
                                    byte[] newBytes = new byte[len];
                                    System.arraycopy(bytes, 0, newBytes, 0, len);
                                    String newStr = new String(newBytes);
                                    editText.setText(newStr);
                                } else {
                                    int index = editText.getSelectionStart();
                                    Selection.setSelection(editable, index);
                                }
                            } else if (Float.valueOf(num) == 0 && !num.equals("0.") && !num.equals("-0.") && !num.equals("-0")) {
                                int len = 1;
                                byte[] bytes = String.valueOf(editable).getBytes();
                                if (last) {
                                    last = false;
                                    int selEndIndex = editText.getText().length();
                                    Selection.setSelection(editable, selEndIndex);
                                }
                                if (bytes.length > len) {
                                    last = true;
                                    byte[] newBytes = new byte[len];
                                    if (!num.startsWith("-")) {
                                        System.arraycopy(bytes, 0, newBytes, 0, len);
                                        String newStr = new String(newBytes);
                                        editText.setText(newStr);
                                    } else {
                                        editText.setText("-0");
                                    }
                                } else {
                                    if (!num.startsWith("-")) {
                                        int index = editText.getSelectionStart();
                                        Selection.setSelection(editable, index);
                                    }
                                }
                            } else {
                                int len = 6;
                                byte[] bytes = String.valueOf(editable).getBytes();
                                if (num.startsWith("-00") && !num.startsWith("0.")) {
                                    last = true;
                                    num = num.substring(0, 2) + num.substring(2).replaceFirst("^0*", "");
                                    editText.setText(num);
                                } else if (num.matches("-0.") && !num.matches("-0\\.")) {
                                    last = true;
                                    num = num.substring(0, 1) + num.substring(2).replaceFirst("^0*", "");
                                    editText.setText(num);
                                }
                                String[] arr = num.split("\\.");
                                if(arr.length == 2){
                                    if(arr[1].length() > 1){
                                        last = true;
                                        String reset = String.valueOf(arr[1].charAt(0));
                                        num = arr[0] + "." + reset;
                                        editText.setText(num);
                                    }
                                }
                                if (bytes.length > len) {
                                    last = true;
                                    byte[] newBytes = new byte[len];
                                    System.arraycopy(bytes, 0, newBytes, 0, len);
                                    String newStr = new String(newBytes);
                                    editText.setText(newStr);
                                } else {
                                    if (last) {
                                        last = false;
                                        int selEndIndex = editText.getText().length();
                                        Selection.setSelection(editable, selEndIndex);
                                    }
                                    int index = editText.getSelectionStart();
                                    Selection.setSelection(editable, index);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
