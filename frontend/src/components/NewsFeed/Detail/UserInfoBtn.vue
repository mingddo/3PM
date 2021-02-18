<template>
  <div>
    <i class="fas fa-ellipsis-v" @click="foldDrop"></i>
    <div v-if="!drop" class="feed-detail-user-drop">
      <div @click="goToProfile">
        프로필로
      </div>
      <div v-if="userpk != id" @click="subscribe">
        <span v-if="!followState">
          구독하기
        </span>
        <span v-else>
          구독취소
        </span>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import { followToggle } from "@/api/mypage.js";


export default {
  name: "UserInfoBtn",
  props: {
    id: Number,
    follow: Boolean,
    name: String,
  },
  data() {
    return {
      drop: true,
      followState: this.follow,
    };
  },
  mounted() {},
  methods: {
    subscribe() {
      followToggle(
        this.id,
        () => {
          if (this.followState) {
            // alert(`${this.name} 님의 소식을 받을 수 있어요!`);
            this.$swal.fire({
              icon: 'success',
              text: `${this.name} 님의 소식을 받을 수 있어요`,
              showConfirmButton: false,
              timer: 1500
            })
            this.followState = false;
            this.drop = true;
          } else {
            // alert(`${this.name} 님의 소식을 더이상 받지 않을 거에요!`);
            this.$swal.fire({
              icon: 'success',
              text: `${this.name} 님의 소식을 더이상 받지 않을 거에요`,
              showConfirmButton: false,
              timer: 1500
            })
            this.followState = true;
            this.drop = true;
          }
        },
        (err) => {
          console.log(err);
        }
      );
    },
    foldDrop() {
      this.drop = !this.drop;
    },
    goToSetting() {
      this.$router.push({ name: "MyPageEdit" });
    },
    goToProfile() {
      this.$router.push({ name: "MyPage", query: { name: this.id } });
    },
  },
  computed: {
    ...mapState({
      userpk: (state) => state.userId,
    }),
  },
};
</script>

<style>
.feed-detail-user-drop {
  /* text-align: center; */
  /* padding: 10px; */
  width: 100px;
  z-index: 2;
  position: absolute;
  right: 0;
  background-color: rgb(236, 236, 236);
  /* padding: 10px; */
  border-radius: 5px;
  text-align: center;
}
.feed-detail-user-drop > div {
  border-bottom: 1px solid rgb(155, 155, 155);
  padding: 10px;
}
.feed-detail-user-drop > div:hover {
  box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.2);
}
.feed-detail-user-drop > div:last-child {
  border-bottom: 0px;
}
</style>
