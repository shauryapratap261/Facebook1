package models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class User {
    private int userId;
    private Set<User>following;
    private Map<Integer, Post> postMap;
    private Post head;
    private Post tail;

    public User(int userId) {
        this.userId = userId;
        this.following = new HashSet<>();
        this.postMap = new HashMap<>();
        this.head = new Post(-1);
        this.tail = new Post(-1);

        // connect the head and tail
        head.setNext(tail);
        tail.setPrev(head);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public Map<Integer, Post> getPostMap() {
        return postMap;
    }

    public void setPostMap(Map<Integer, Post> postMap) {
        this.postMap = postMap;
    }

    public Post getHead() {
        return head;
    }

    public void setHead(Post head) {
        this.head = head;
    }

    public Post getTail() {
        return tail;
    }

    public void setTail(Post tail) {
        this.tail = tail;
    }

    public void createPost(int postId){

        // 1. add to post map
        // 2. add to the doubly linkedList

        Post post = new Post(postId);
        postMap.put(postId, post);

        Post firstPost = head.getNext();
        post.setNext(firstPost);
        post.setPrev(head);

        firstPost.setPrev(post);
        head.setNext(post);

        System.out.println(this.userId + " Created a Post with Id " + postId);

    }

    public void deletePost(int postId){

        // 1. remove from post map
        // 2. remove from the doubly linkedList

        if(postMap.get(postId) == null){
            System.out.println("CAN'T FIND THE POST AND DELETE IT");
            return;
        }

        Post post = postMap.get(postId);

        post.getPrev().setNext(post.getNext());
        post.getNext().setPrev(post.getPrev());

        postMap.remove(postId);

        System.out.println(this.userId + " Deleted a Post with Id " + postId);

    }

    public void follow(User user){
        following.add(user);
        System.out.println(this.userId + " Followed user " + user.getUserId());
    }

    public void unfollow(User user){

        if(!following.contains(user)){
            System.out.println("User : " + user.getUserId() + " not found in " + this.userId + "'s following");
            return;
        }

        following.remove(user);
        System.out.println(this.userId + " unFollowed user " + user.getUserId());
    }


}
