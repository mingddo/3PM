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
* {
  box-sizing: border-box;
}

.GroupResultFrame {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 24px;
}
.Group-resultImgFrame {
  width: 70px;
  height: 70px;
  margin-left: 12px;
  overflow: hidden;
  border-radius: 70%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.Group-resultImgFrame-img {
  width: 100px;
  object-fit: cover;
}

.Group-reultDetail {
  width: 75%;
  height: 92px;
  margin: auto 0;
  margin-left: 12px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
}
.Group-rsultDetailTilte {
  font-size: 22px;
  font-weight: 600;
  margin-bottom: 5px;
}

.Group-resultDetailcontent {
  width: 100%;
  margin-bottom: 5px;
  font-size: 16px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  word-wrap: break-word;
}

.Group-resultDetailcnt {
  font-size: 16px;
}
</style>
