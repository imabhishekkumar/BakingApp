package com.android.imabhishekkumar.bakingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.imabhishekkumar.bakingapp.Model.Step;
import com.android.imabhishekkumar.bakingapp.R;
import com.android.imabhishekkumar.bakingapp.Utils.ClickListener;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {
    private Context context;
    private List<Step> stepList;
    private ClickListener clickListener;

    public StepAdapter(Context context, List<Step> data) {
        this.context = context;
        stepList = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.steps_row,
                        viewGroup,
                        false);


        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Step currentStep = stepList.get(i);
        String shortDescription = currentStep.getShortDescription();
        viewHolder.stepsTV.setText(shortDescription);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(view.getContext(), currentStep.getId(),
                        currentStep.getDescription(),
                        currentStep.getVideoURL(),
                        currentStep.getThumbnailURL());
            }
        });

    }

    public void setOnClick(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView stepsTV;
        CardView cardView;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            stepsTV = itemView.findViewById(R.id.stepsTV);
            cardView = itemView.findViewById(R.id.stepsCV);
      /*     itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //int pos = getAdapterPosition();

                   /* Bundle bundle = new Bundle();
                    bundle.putInt(Constants.BUNDLE_STEPS_ID,stepList.get(pos).getId());
                    bundle.putString(Constants.BUNDLE_STEPS_DESC,stepList.get(pos).getDescription());
                    bundle.putString(Constants.BUNDLE_STEPS_VIDEO_URL,stepList.get(pos).getVideoURL());
                    bundle.putString(Constants.BUNDLE_STEPS_THUMB_URL,stepList.get(pos).getThumbnailURL());
                    DetailsFragement detailsFragement = new DetailsFragement();
                    detailsFragement.setArguments(bundle);
                    // Toast.makeText(context,stepList.get(pos).getDescription(),Toast.LENGTH_SHORT).show();

                }
            });*/
        }
    }
}
