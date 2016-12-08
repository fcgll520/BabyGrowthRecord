package com.baby.babygrowthrecord.Mother;

/**
 * Created by apple on 2016/11/22.
 */
public class GoogleCard {

        private int id;
        private String mDescription;
        private String mDrawable;

        public GoogleCard(int essay_id, String mDescription, String mDrawable)
        {
            this.id=essay_id;
            this.mDescription=mDescription;
            this.mDrawable=mDrawable;
        }

        public String getDescription()
        {
            return mDescription;
        }

        public void setDescription(String mDescription)
        {
            this.mDescription = mDescription;
        }

        public String getDrawable()
        {
            return mDrawable;
        }

        public void setDrawable(String mDrawable)
        {
            this.mDrawable = mDrawable;
        }

         public int getId() {
            return id;
         }

         public void setId(int id) {
            this.id = id;
         }

}
