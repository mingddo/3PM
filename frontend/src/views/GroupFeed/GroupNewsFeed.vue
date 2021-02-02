<template>
  <div class="newsfeed">
    <GroupFeedHeader class="newsfeed-header"/>
    <GroupFeedRecommend />
    <GroupFeedList :feed="feed" :last="last"/>
  </div>
</template>

<script>
import GroupFeedHeader from '../../components/GroupFeed/GroupFeedHeader.vue';
import GroupFeedList from '../../components/GroupFeed/GroupFeedList.vue';
import GroupFeedRecommend from '../../components/GroupFeed/GroupFeedRecommend.vue';

export default {
  name: "groupnewsfeed",
  components: { GroupFeedList, GroupFeedHeader, GroupFeedRecommend },
  data () {
    return {
      feed : [],
      page: 0,
      last : false,
      next : false,
    }
  },
  methods : {
    setFeedList () {
      // group 으로 feed Get axios 요청 리스트 받아오기
      // 10 개 단위로 기존 feed 리스트에 추가해주고 next = false 값 주기
      // 추가 후 feedslength 10 이하면 last true 주기
      //
      let testFeed = [
        {
          id: 1,
          groupId : 1,
          groupName: '첫번째 그룹',
          content : '테스트 내용',
          commentCnt : 2,
          likeCnt : 10,
          localDateTime : "2021-02-02T01:49:42",
          tags: ['시작', '테스트'],
          file: null,
          user: {
            id: 19,
            img: null,
            nickname: 'user'
          }
        },
        {
          id: 2, 
          groupId : 1,
          groupName: '두번째 그룹',
          content : '테스트 내용22',
          commentCnt : 4,
          likeCnt : 11,
          localDateTime : "2021-02-03T01:49:42",
          tags: ['시작222', '테스트2222'],
          file: null,
          user: {
            id: 27,
            img: null,
            nickname: 'anony'
          }
        },
      ]
      if (testFeed.length < 10) {
        this.last = true;
      }
      for (let f of testFeed) {
        this.feed.push(f)
      }
    },
    setScroll () {
      window.addEventListener("scroll", () => {
        let scrollLocation = document.documentElement.scrollTop; // 현재 스크롤바 위치
        let windowHeight = window.innerHeight; // 스크린 창
        let fullHeight = document.body.scrollHeight; //  margin 값은 포함 x
        if (
          !this.next &&
          !this.last &&
          scrollLocation + windowHeight >= fullHeight
        ) {
          this.next = true;
          setTimeout(() => {
            this.setFeedList();
          }, 1000);
        }
      })
    },
  },
  created () {
    this.setFeedList();
  },
  mounted () {
    this.setScroll();
  },
  };
</script>

<style></style>
