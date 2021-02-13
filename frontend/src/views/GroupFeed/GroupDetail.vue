<template>
  <div>
    <NewsFeedHeader />
    <img src="https://picsum.photos/200" alt="" />
    <div>{{ group_info.group_title }}</div>
    <div>
      <div>그룹장</div>
      <div>{{ group_info.group_leader }}</div>
    </div>
    <div>
      <div>인원</div>
      <div>{{ group_info.group_cnt }}</div>
    </div>
    <div>{{ group_info.group_introduce }}</div>
    <button v-if="!isjoined">그룹 가입하기</button>
    <button v-if="isjoined">그룹 탈퇴하기</button>
    <NewsFeedList />
  </div>
</template>

<script>
import NewsFeedHeader from "@/components/NewsFeed/NewsFeedHeader.vue";
import NewsFeedList from "@/components/NewsFeed/NewsFeedList.vue";

export default {
  components: { NewsFeedHeader, NewsFeedList },
  data() {
    return {
      isjoined: "false",
      group_info: {
        group_img: "",
        group_title: "볼링 너 밖에 없어",
        group_introduce: "안녕하세요 여기는 볼링을 사랑하는 볼링 그룹입니다.",
        group_leader: "박성호",
        group_cnt: "502",
        group_people_id: [1, 2, 3, 4, 5, 9],
      },
    };
  },
  methods: {
    getgroupInfo() {
      // axios작성 부분
    },
    validjoin() {
      this.group_info.group_people_id.map((user) => {
        if (user === this.$store.state.userId) {
          return (this.isjoined = true);
        } else {
          return (this.isjoined = false);
        }
      });
      console.log(this.isjoined);
    },
  },
  created() {
    this.validjoin();
  },
};
</script>

<style></style>
