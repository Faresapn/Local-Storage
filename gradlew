<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.dtechnology.moviebox.fragment.FavoriteFragment"
    android:orientation="vertical">

    <!-- TODO: Update blank fragment layout -->
    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical" >
            <TextView
                android:id="@+id/textView"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:padding="16dp"
                android:text="@string/movie"
                android:textColor="#000000"
                android:textStyle="bold"
                android:textSize="20sp" />

            <FrameLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:padding="6dp">

                <android.support.v7.widget.RecyclerView
                    android:id="@+id/rvmovie"
                    android:layout_width="match_parent"
                    android:layout_height="220dp"/>
            </FrameLayout>

            <TextView
                android:id="@+id/textView1"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:padding="16dp"
                android:text="@string/tv"
                android:textColor="#000000"
                android:textSize="20sp"
                android:textStyle="bold"/>

            <FrameLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:padding="6dp">

                <android.support.v7.widget.RecyclerView
                    android:id="@+id/rv_tv"
                    android:layout_width="match_parent"
                    android:layout_height="260dp">

                </android.support.v7.widget.RecyclerView>
            </FrameLayout>
        </LinearLayout>
    </ScrollView>
</LinearLayout>
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                