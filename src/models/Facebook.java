package models;

import java.util.*;

public class Facebook {

    Map<Integer, User> userMap;
    public static int time = 0;
    public static int FEED_SIZE = 10;
    public static int PAGE_SIZE = 2;

    public Facebook() {
        this.userMap = new HashMap<>();
    }

    //Compose a new post.
    public void createPost(int userId, int postId){

        User user = userMap.get(userId);
        if(user == null){
            user = new User(userId);
            userMap.put(userId, user);
        }

        user.createPost(postId);

    }

    //    Retrieve the 10 most recent post ids in the user's news feed.
    //    Each item in the news feed must be posted by users who the user followed or
    //    by the user herself (Order -> most to least recent)
    public void getNewsFeed(int userId){

        User user = userMap.get(userId);

        if(user == null){
            user = new User(userId);
            userMap.put(userId, user);
        }

        List<Post> topNPosts = getTopNPosts(user, FEED_SIZE);
        printNewsFeed(topNPosts, user);
    }

    private List<Post> getTopNPosts(User user, int feedSize){
        List<Post>topNPosts = new ArrayList<>();

        PriorityQueue<Post> topPosts = new PriorityQueue<>((a,b)->{
            if(a.getTime() > b.getTime()){
                return -1;
            } else if(a.getTime() < b.getTime()){
                return 1;
            } else{
                return 0;
            }
        });

        Set<User>following = user.getFollowing();
        System.out.println("User's following size is " + following.size());

        for(User followee : following){
            Map<Integer, Post> postMap = followee.getPostMap();
            //System.out.println("followee's Post size is " + postMap.size());
            for(Map.Entry<Integer, Post> entry : postMap.entrySet()){
                topPosts.add(entry.getValue());
            }
        }

        int i=0;
        while(topPosts.size() > 0 && i < feedSize){
            topNPosts.add(topPosts.poll());
            i++;
        }

        return topNPosts;

    }

    private void printNewsFeed(List<Post> topNPosts, User user){
        System.out.println("Printing the feed for user : " +  user.getUserId() + "of size " + topNPosts.size());

        for(Post post : topNPosts){
            System.out.println("Showing the Post : " + post.getPostId());
        }
    }

    // Follower follows a followee.
    public void follow(int followerId, int followeeId){

        User follower = userMap.get(followerId);
        User followee = userMap.get(followeeId);

        if(follower == null){
            follower = new User(followerId);
            userMap.put(followerId, follower);
        }

        if(followee == null){
            followee = new User(followeeId);
            userMap.put(followeeId, followee);
        }

        follower.follow(followee);

    }

//    Follower unfollows a followee.
    public void unfollow(int followerId, int followeeId){

        User follower = userMap.get(followerId);
        User followee = userMap.get(followeeId);

        if(follower == null){
            follower = new User(followerId);
            userMap.put(followerId, follower);
        }

        if(followee == null){
            followee = new User(followeeId);
            userMap.put(followerId, follower);
        }

        follower.unfollow(followee);

    }

//    Delete an existing post.
    public void deletePost(int userId, int postId){
        User user = userMap.get(userId);
        if(user == null){
            user = new User(userId);
        }

        user.deletePost(postId);
    }

//    Retrieve the most recent post ids in the user's news feed in a paginated manner.
//    Each item in the news feed must be posted by users who the user followed or by the
//    user herself (Order -> most to least recent) Assume pageSize= 2.
    public void getNewsFeedPaginated(int userId, int pageNumber){

        User user = userMap.get(userId);

        if(user == null){
            user = new User(userId);
            userMap.put(userId, user);
        }

        List<Post> allPosts = getTopNPosts(user, Integer.MAX_VALUE);

//      1 2 / 3 4/ 5 6 / 7 8 /

        int postStart = ((pageNumber-1)*PAGE_SIZE);
        int postEnd = Math.min(postStart + PAGE_SIZE, allPosts.size()) - 1;

        System.out.println("Showing the posts on Page: " + pageNumber);
        for(int i=postStart;i<=postEnd;i++){
            System.out.println("Post : " + allPosts.get(i).getPostId());
        }

    }

}
