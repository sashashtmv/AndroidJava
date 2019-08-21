package com.sashashtmv.myReminderEveryDay.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sashashtmv.myReminderEveryDay.R;
import com.sashashtmv.myReminderEveryDay.Utils;
import com.sashashtmv.myReminderEveryDay.fragment.CurrentTaskFragment;
import com.sashashtmv.myReminderEveryDay.model.Item;
import com.sashashtmv.myReminderEveryDay.model.ModelSeparator;
import com.sashashtmv.myReminderEveryDay.model.ModelTask;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class CurrentTasksAdapter extends TaskAdapter {

    private static final int TYPE_TASK = 0;
    private static final int TYPE_SEPARATOR = 1;

    public CurrentTasksAdapter(CurrentTaskFragment taskFragment) {
        super(taskFragment);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_TASK:
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.model_task,parent,false);
                TextView title = v.findViewById(R.id.tv_task_title);
                TextView date = v.findViewById(R.id.tv_task_date);
                CircleImageView priority = v.findViewById(R.id.cvTaskPriority);

                return new TaskViewHolder(v,title,date, priority);
            case TYPE_SEPARATOR:
                View separator = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.model_separator,parent,false);
                TextView type = separator.findViewById(R.id.tvSeparatorName);
                return  new SeparatorViewHolder(separator,type);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Item item = mItemList.get(position);

            final Resources resources = holder.itemView.getResources();
        if(item.isTask()){
            holder.itemView.setEnabled(true);
            //активируем возможность нажатия Таска
            final ModelTask task = (ModelTask)item;
            final TaskViewHolder taskViewHolder = (TaskViewHolder)holder;

            final View itemView = taskViewHolder.itemView;

            taskViewHolder.title.setText(task.getTitle());
            if(task.getDate() != 0){
                taskViewHolder.date.setText(Utils.getFullDate(task.getDate()));
            }else {
                taskViewHolder.date.setText(null);
            }

            //поставим для элемента списка признак видимости
            itemView.setVisibility(View.VISIBLE);
            taskViewHolder.priority.setEnabled(true);

            //подсветим просроченные задачи, у них будет меняться фоновый цвет
            if(task.getDate() != 0 && task.getDate() < Calendar.getInstance().getTimeInMillis()){
                itemView.setBackgroundColor(resources.getColor(R.color.gray_200));
            }else {
                itemView.setBackgroundColor(resources.getColor(R.color.gray_50));
            }

            //установим основной цвет текста для заголовка задачи
            taskViewHolder.title.setTextColor(resources.getColor(R.color.primary_text_default_material_light));
            taskViewHolder.date.setTextColor(resources.getColor(R.color.secondary_text_default_material_light));
            taskViewHolder.priority.setColorFilter(resources.getColor(task.getPriorityColor()));
            taskViewHolder.priority.setImageResource(R.drawable.ic_checkbox_blank_circle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //диалог редактирования задачи будет вызываться по клику на задаче в списке
                    getTaskFragment().showTaskEditDialog(task);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
            //handler нужен для того, чтобы сработала рипл-анимация, чтобы она успела сработать до того, как вызовется диалог
                    Handler handler = new Handler();
                    //щрганизуем задержку
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            getTaskFragment().removeTaskDialog(taskViewHolder.getLayoutPosition());
                        }
                    }, 1000);
                    return true;
                }
            });

            taskViewHolder.priority.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //блокирует многократное нажатие на элемент списка
                    taskViewHolder.priority.setEnabled(false);

                    task.setStatus(ModelTask.STATUS_DONE);
                    //обновляем базу данных
                    getTaskFragment().mMainActivity.mDBHelper.update().status(task.getTimeStamp(), ModelTask.STATUS_DONE);

                    //установим основной цвет текста для заголовка задачи
                    taskViewHolder.title.setTextColor(resources.getColor(R.color.primary_text_disabled_material_light));
                    taskViewHolder.date.setTextColor(resources.getColor(R.color.secondary_text_disabled_material_light));
                    taskViewHolder.priority.setColorFilter(resources.getColor(task.getPriorityColor()));
//
//                    //добавим анимацию - поворт картинки по вертикальной оси
                    ObjectAnimator flipIn = ObjectAnimator.ofFloat(taskViewHolder.priority, "rotationY", -180f, 0f);
                    flipIn.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                            if(task.getStatus() == ModelTask.STATUS_DONE){
                                taskViewHolder.priority.setImageResource(R.drawable.baseline_check_circle_white_48);

                                //добавим анимацию - будет перемещать элемент списка в сторону на расстояние, равное длине элемента списка,
                                //таким образом, элемент списка полностью исчезает из поля зрения
                                ObjectAnimator translationX = ObjectAnimator.ofFloat(itemView, "translationX",
                                 0f,itemView.getWidth());
                                ObjectAnimator translationXBack = ObjectAnimator.ofFloat(itemView, "translationX",
                                 itemView.getWidth(), 0f);

                                translationX.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
//                                        //делаем невидимым, чтобы удаленный элемент не отображался
                                        itemView.setVisibility(View.GONE);
                                        getTaskFragment().moveTask(task);
                                        removeItem(taskViewHolder.getLayoutPosition());
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                });
                                //создадим аниматорсет для запуска нескольких анимаций в определенной последовательности
                                AnimatorSet translationSet = new AnimatorSet();
                                translationSet.play(translationX).before(translationXBack);
                                translationSet.start();
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    flipIn.start();
                }
            });
        }else {
            ModelSeparator separator = (ModelSeparator)item;
            SeparatorViewHolder separatorViewHolder = (SeparatorViewHolder)holder;
            separatorViewHolder.type.setText(resources.getString(separator.getType()));
        }
    }

    //в зависимости от позиции возвращает тип View

    @Override
    public int getItemViewType(int position) {
        if(getItem(position).isTask()){
            return TYPE_TASK;
        }else return TYPE_SEPARATOR;
    }
}
