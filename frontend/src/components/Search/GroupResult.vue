<template>
  <div class="Group-ResultFrame">
    <div class="Group-resultImgFrame">
      <img
        src="@/assets/loverduck.png"
        alt=""
        class="Group-resultImgFrame-img"
      />
    </div>
    <div class="Group-reultDetail">
      <!-- 글 내용 -->
      <div class="Group-rsultDetailTilte">group name</div>
      <div class="Group-resultDetailcontent">
        본 교육 과정은 삼성전자가 주관하고 고용노동부와 함께 합니다.(전체 교육
        운영은 멀티캠퍼스에 위탁 실시) 주관삼성 로고후원고용노동부 로고
      </div>
      <div class="Group-resultDetailcnt">
        <i class="fas fa-users"></i>1500명
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    grouplist: Object,
  },
  data() {
    return {};
  },
  methods: {},
  mounted() {},
};
</script>

<style scoped>

</style>
