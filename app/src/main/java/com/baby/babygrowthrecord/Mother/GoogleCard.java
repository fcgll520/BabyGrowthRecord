package com.baby.babygrowthrecord.Mother;

/**
 * Created by apple on 2016/11/22.
 */
public class GoogleCard {
    private int mId;
        private String mDescription;
        private String mDrawable;

        public GoogleCard(int id,String mDescription,String mDrawable)
        {
            this.mId=id;
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

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }
}
