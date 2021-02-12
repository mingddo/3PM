<template>
  <div>
    <i class="fas fa-bars" @click="foldDrop"></i>
    <div v-if="!drop" class="feed-detail-user-drop">
      <div @click="goToProfile">
        프로필로
      </div>
      <div @click="subscribe" v-if="follow">
        구독하기
      </div>
      <div v-else>
        구독취소
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'UserInfoBtn',
  props: {
    id: Number,
    follow: Boolean
  },
  data() {
    return {
      drop: true,
    };
  },
  mounted() {
    
  },
  methods: {
    foldDrop () {
      this.drop = !this.drop
    },
    goToProfile () {
      this.$router.push({ name: 'MyPage', query: { name: this.id}})
    },
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
  border-bottom: 1px solid rgb(155, 155, 155);;
  padding : 10px
}
.feed-detail-user-drop > div:hover {
  box-shadow: 0px 5px 10px rgba(0,0,0,0.2);
}
.feed-detail-user-drop > div:last-child {
  border-bottom: 0px;
}
</style>