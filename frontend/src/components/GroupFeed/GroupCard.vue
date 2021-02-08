<template>
  <div>
    <img src="https://picsum.photos/200" alt="" />
    <div>
      <div>group name</div>
      <div>회원수18명</div>
    </div>
    <div>그룹 소개하는 부분입니다.</div>
    <div>최신 피드가 n 개 업로드 되었습니다.</div>
  </div>
</template>

<script>
export default {
  props: {
    group: Object,
  },
};
</script>

<style></style>
