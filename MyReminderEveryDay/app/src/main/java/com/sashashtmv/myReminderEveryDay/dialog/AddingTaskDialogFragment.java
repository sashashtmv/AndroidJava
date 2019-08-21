package com.sashashtmv.myReminderEveryDay.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.sashashtmv.myReminderEveryDay.R;
import com.sashashtmv.myReminderEveryDay.Utils;
import com.sashashtmv.myReminderEveryDay.alarm.AlarmHelper;
import com.sashashtmv.myReminderEveryDay.model.ModelTask;

import java.util.Calendar;

public class AddingTaskDialogFragment extends DialogFragment {

    private AddingTaskListener mAddingTaskListener;
    public interface AddingTaskListener{
        void onTaskAdded(ModelTask newTask);
        void onTaskAddingCancel();
    }

//для получения доступа к диалогу из активити, например, во время нажатия, нужно определить слушателя
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mAddingTaskListener = (AddingTaskListener) activity;

        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement AddingTaskListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // сделаем заголовок окна
        builder.setTitle(R.string.adding_task);

        //для работы с макетом диалога
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_tasks, null);

        final TextInputLayout tilTitle = view.findViewById(R.id.til_dialog_task_title);
        final EditText etTitle = tilTitle.getEditText();

        TextInputLayout tilDate = view.findViewById(R.id.til_dialog_task_date);
        final EditText etDate = tilDate.getEditText();

        TextInputLayout tilTime = view.findViewById(R.id.til_dialog_task_time);
        final EditText etTime = tilTime.getEditText();

        Spinner spPriority = view.findViewById(R.id.sp_dialog_task_priority);

        // создаем всплывающие подсказки
        tilTitle.setHint(getResources().getString(R.string.hint_title));
        tilDate.setHint(getResources().getString(R.string.hint_date));
        tilTime.setHint(getResources().getString(R.string.hint_time));

        builder.setView(view);

        final ModelTask modelTask = new ModelTask();
//для наполнения спинера данными создаем адаптер
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(getActivity(),
         android.R.layout.simple_spinner_dropdown_item, ModelTask.PRIORITY_LEVELS);
        spPriority.setAdapter(priorityAdapter);
        //установим слушатель для элементов спиннера
        spPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                modelTask.setPriority(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //возвращает текущее время
        final Calendar calendar = Calendar.getInstance();
        // добавим к времени календаря час, чтобы он срабатывал через час, если указана только дата, без времени при создании задачи
        calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY) + 1);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //чтоб не возникал эффект накладывания анимации компонента floatingLabel на устанавливаемый текст добавляем символ пробела в поле ввода
                if(etDate.length() == 0){
                    etDate.setText(" ");
                }
                DialogFragment datePickerFragment = new DatePickerFragment(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        etDate.setText(Utils.getDate(calendar.getTimeInMillis()));
                    }

                    //чтоб при нажатии кнопки cancel текст не устанавливался
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        etDate.setText(null);
                    }
                };
                //реализуем отображение диалога
                datePickerFragment.show(getFragmentManager(), "DatePickerFragment");
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etTime.length() == 0){
                    etTime.setText(" ");
                }
                DialogFragment timePickerFragment = new TimePickerFragment(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);
                        etTime.setText(Utils.getTime(calendar.getTimeInMillis()));
                    }

//                    @Override
//                    public void onCancel(DialogInterface dialog) {
//                        etTime.setText(null);
//                    }
                };
                timePickerFragment.show(getFragmentManager(), "TimePickerFragment");
            }
        });

        //установим кнопки подтверждения, отмены
        builder.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //слушателю кнопки сохранения присваиваем объекту Таск заголовок из соответствующего поля ввода текста
                modelTask.setTitle(etTitle.getText().toString());
                modelTask.setStatus(ModelTask.STATUS_CURRENT);
                if(etDate.length() != 0 || etTime.length() != 0){
                    modelTask.setDate(calendar.getTimeInMillis());
                    AlarmHelper alarmHelper = AlarmHelper.getInstance();
                    alarmHelper.setAlarm(modelTask);
                }
                modelTask.setStatus(ModelTask.STATUS_CURRENT);
                mAddingTaskListener.onTaskAdded(modelTask);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAddingTaskListener.onTaskAddingCancel();
                dialog.cancel();
            }
        });

        //обращаться к кнопкам в диалоге и менять их параметры можно только после того, как диалог будет показан
        // устанавливаем слушатель на событие отображения диалога
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button positiveButton = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                //будем вызывать сообщение об ошибке в флоатинг лейбелс и блокировать кнопку ОК, чтобы предотвратить создание пустых тасков
                if(etTitle.length() == 0 || etDate.length() == 0 || etTime.length() == 0){
                    positiveButton.setEnabled(false);
                    tilTitle.setError(getResources().getString(R.string.dialog_error_empty_title));
                }
                // добавим слушатели события изменения текста в каждом поле
                etTitle.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        //проверяем длину текста
                        if(s.length() == 0 || etDate.length() == 0 || etTime.length() == 0){
                            tilTitle.setError(getResources().getString(R.string.dialog_error_empty_title));
                            positiveButton.setEnabled(false);
                        }else {
                            positiveButton.setEnabled(true);
                            tilTitle.setErrorEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                etDate.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        //проверяем длину текста
                        if(s.length() == 0 || etTitle.length() == 0 || etTime.length() == 0){
                            tilTitle.setError(getResources().getString(R.string.dialog_error_empty_title));
                            positiveButton.setEnabled(false);
                        }else {
                            positiveButton.setEnabled(true);
                            tilTitle.setErrorEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                etTime.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        //проверяем длину текста
                        if(s.length() == 0 || etDate.length() == 0 || etTitle.length() == 0){
                            tilTitle.setError(getResources().getString(R.string.dialog_error_empty_title));
                            positiveButton.setEnabled(false);
                        }else {
                            positiveButton.setEnabled(true);
                            tilTitle.setErrorEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });

        return alertDialog;
    }
}
