<template>
  <div @click="goToGroupDetail">
    <img src="https://picsum.photos/200" alt="" />
    <div>
      <div>그룹 이름 : {{ group.name }}</div>
      <div>회원수 : {{ group.memberCnt}}</div>
    </div>
    <div>그룹 소개: {{ group.description }}</div>
    <div>최신 피드가 n 개 업로드 되었습니다.</div>
    <div>그룹장 : {{ group.leaders }} </div>
  </div>
</template>

<script>
export default {
  props: {
    group: Object,
  },
  methods: {
    goToGroupDetail () {
      this.$router.push({ name : 'groupdetail', query: { groupId: this.group.id}})
    },
  },
};
</script>

<style></style>
