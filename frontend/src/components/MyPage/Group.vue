<template>
  <div class="activityComponentFrame">
    <div class="profileImgFrame">
      <img class="pofileImg" src="@/assets/loverduck.png" alt="프로필사진" />
    </div>
    <div class="activityContent">
      <div>그룹명 : {{ group.name }}</div>
      <div>그룹 소개 : {{ group.description }}</div>
      <div>그룹원 수 :{{ group.memberCnt }}</div>
      <div>그룹 생성일 :  {{ createdDateChange() }}</div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    group: Object,
  },
  mounted() {
    this.createdDateChange()
  },
  methods : {
    createdDateChange() {
      let createdDate = this.group.createdDate.split('-')
      const year = createdDate[0]
      const month = createdDate[1]
      const day = createdDate[2].split('T')[0]
      const createdDateChanged = `${year}년 ${month}월 ${day}일`
      return createdDateChanged
    }
  }
};
</script>

<style scoped>


.profileImgFrame {
  width: 70px;
  height: 70px;
  margin-right: 10px;
  border: solid 1px rgba(0, 0, 0, 0.2);
  border-radius: 70%;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  display: flex;
  justify-content: center;
}

</style>
