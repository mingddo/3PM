<template>
  <div class="newsfeed">
    <NewsFeedHeader
      :Category="Category"
      class="newsfeed-header"
    />
    <NewsFeedRecommend 
      :reco="reco"
    />
    <NewsFeedList
      :feed="feed"
      :Category="Category"
    />
  </div>
</template>

<script>
import NewsFeedHeader from '../../components/NewsFeed/NewsFeedHeader.vue';
import NewsFeedList from '../../components/NewsFeed/NewsFeedList.vue';
import NewsFeedRecommend from '../../components/NewsFeed/NewsFeedRecommend.vue';

export default {
  name: 'NewsfeedPersonal',
  components: {
    NewsFeedHeader,
    NewsFeedRecommend,
    NewsFeedList,
  },
  data () {
    return {
      reco: [
        {
          'id' : 1,
          'content' : '첫 피드입니다',
          'date' : '2020-01-25',
          'tag' : '#시작',
          'file' : `https://newsimg.hankookilbo.com/cms/articlerelease/2019/04/29/201904291390027161_3.jpg`,
          'user_id' : 10,
          'like_cnt' : 7,
          'comment_cnt' : 15,
        },
        {
          'id' : 2,
          'content' : '두번째 피드입니다',
          'date' : '2020-01-30',
          'tag' : '#두번째',
          'file' : `https://newsimg.hankookilbo.com/cms/articlerelease/2019/04/29/201904291390027161_3.jpg`,
          'user_id' : '11'
        },
        {
          'id' : 2,
          'content' : '세번째 피드입니다',
          'date' : '2020-01-30',
          'tag' : '#세번째',
          'file' : `https://newsimg.hankookilbo.com/cms/articlerelease/2019/04/29/201904291390027161_3.jpg`,
          'user_id' : '11'
        },
        {
          'id' : 2,
          'content' : '네번째 피드입니다',
          'date' : '2020-01-30',
          'tag' : '#네번째',
          'file' : `https://newsimg.hankookilbo.com/cms/articlerelease/2019/04/29/201904291390027161_3.jpg`,
          'user_id' : '11'
        },
        {
          'id' : 2,
          'content' : '다섯번째 피드입니다',
          'date' : '2020-01-30',
          'tag' : '#다섯번째',
          'file' : `https://newsimg.hankookilbo.com/cms/articlerelease/2019/04/29/201904291390027161_3.jpg`,
          'user_id' : '11'
        },
      ],
      feed: [
        {
          'id' : 1,
          'content' : '첫 피드입니다',
          'date' : '2020-01-25',
          'tag' : '#시작',
          'file' : `https://newsimg.hankookilbo.com/cms/articlerelease/2019/04/29/201904291390027161_3.jpg`,
          'user_id' : 10,
          'like_cnt' : 7,
          'comment_cnt' : 15,
        },
        {
          'id' : 2,
          'content' : '두번째 피드입니다',
          'date' : '2020-01-30',
          'tag' : '#두번째',
          'file' : `https://newsimg.hankookilbo.com/cms/articlerelease/2019/04/29/201904291390027161_3.jpg`,
          'user_id' : '11',
          'like_cnt' : 7,
          'comment_cnt' : 15,
        },
        {
          'id' : 2,
          'content' : '세번째 피드입니다',
          'date' : '2020-01-30',
          'tag' : '#세번째',
          'file' : `https://newsimg.hankookilbo.com/cms/articlerelease/2019/04/29/201904291390027161_3.jpg`,
          'user_id' : '11',
          'like_cnt' : 7,
          'comment_cnt' : 15,
        },
        {
          'id' : 2,
          'content' : '네번째 피드입니다',
          'date' : '2020-01-30',
          'tag' : '#네번째',
          'file' : `https://newsimg.hankookilbo.com/cms/articlerelease/2019/04/29/201904291390027161_3.jpg`,
          'user_id' : '11',
          'like_cnt' : 7,
          'comment_cnt' : 15,
        },
        {
          'id' : 2,
          'content' : '다섯번째 피드입니다',
          'date' : '2020-01-30',
          'tag' : '#다섯번째',
          'file' : `https://newsimg.hankookilbo.com/cms/articlerelease/2019/04/29/201904291390027161_3.jpg`,
          'user_id' : '11',
          'like_cnt' : 7,
          'comment_cnt' : 15,
        },
      ],
      Category: '꽃보다 집'
    }
  },
  methods: {

  },
};
</script>

<style>
.newsfeed {
  width: 940px;
  margin: 0 auto;
}
.newsfeed-header {
  display: flex;
  justify-content: space-between;
}
.newsfeed-header-search {
  vertical-align: bottom;
}
.newsfeed-recommend-content {
  border: 1px solid;
  padding: 10px;

}  
</style>
