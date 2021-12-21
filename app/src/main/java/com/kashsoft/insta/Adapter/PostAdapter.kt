package com.kashsoft.insta.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.kashsoft.insta.Model.Post
import com.kashsoft.insta.Model.User
import com.kashsoft.insta.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_account_setting.*

class PostAdapter
    (private  val mContext: Context,
                  private val mPost: List <Post>): RecyclerView.Adapter<PostAdapter.ViewHolder>() {

private var firebaseUser: FirebaseUser?= null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.posts_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {


        return mPost.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        firebaseUser = FirebaseAuth.getInstance().currentUser

        val post = mPost[position]
        Picasso.get().load(post.getPostimage()).into(holder.postImage)
        publisherInfo(holder.profileImage, holder.userName, holder.publisher, post.getPublisher())
    

        holder.likeButton.setOnClickListener {
            if (holder.likeButton.tag == "Like")

            {

            }else{

            }
        }

    
    }



    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var profileImage: CircleImageView
        var postImage: ImageView
        var likeButton: ImageView
        var commentButton: ImageView
        var saveButton: ImageView


        var userName: TextView
        var likes: TextView
        var publisher: TextView
        var description: TextView
        var comments : TextView

        init {
            profileImage = itemView.findViewById(R.id.user_profile_image_post)
            postImage = itemView.findViewById(R.id.post_image_home)
            likeButton = itemView.findViewById(R.id.post_image_like_btn)
            commentButton = itemView.findViewById(R.id.post_image_comment_btn)
            saveButton = itemView.findViewById(R.id.post_save_comment_btn)
            userName = itemView.findViewById(R.id.user_name_post)
            likes = itemView.findViewById(R.id.likes)
            publisher = itemView.findViewById(R.id.publisher)
            description = itemView.findViewById(R.id.description)
            comments = itemView.findViewById(R.id.comments)

        }

    }
// notes
    private fun publisherInfo(profileImage: CircleImageView, userName: TextView,
                              publisher: TextView, publisherID: String) {


        val usersRef = FirebaseDatabase.getInstance().reference.child("Users")
            .child(publisherID)
        usersRef.addValueEventListener(object : ValueEventListener

        {
            override fun onDataChange(po: DataSnapshot)
            {
              if (po.exists())
              {
                  val user = po.getValue<User>(User::class.java)

                  Picasso.get().load(user!!.getImage()).placeholder(R.drawable.profile)
                      .into(profileImage)
                  userName.text = user!!.getUsername()
                  publisher.text = user!!.getFullname()


              }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }



}