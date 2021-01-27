<template>
  <div>
    <div class="GroupResultFrame">
      <div class="resultImgFrame">
        <img src="@/assets/loverduck.png" alt="" />
      </div>
      <div class="reultDetail">
        <!-- 글 내용 -->
        <div class="rsultDetailTilte">group name</div>
        <div class="resultDetailcontent">
          본 교육 과정은 삼성전자가 주관하고 고용노동부와 함께 합니다.(전체 교육
          운영은 멀티캠퍼스에 위탁 실시) 주관삼성 로고후원고용노동부 로고
        </div>
        <div class="resultDetailcnt"><i class="fas fa-users"></i>1500명</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    grouplist: Object,
  },
};
</script>

<style scoped>
.GroupResultFrame {
  width: 100%;
  height: 94px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
}
.resultImgFrame {
  width: 70px;
  height: 70px;
  margin-left: 12px;
  overflow: hidden;
  border-radius: 70%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.resultImgFrame img {
  width: 100px;
  object-fit: cover;
}

.reultDetail {
  width: 480px;
  height: 92px;
  margin: auto 0;
  margin-left: 12px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
}
.rsultDetailTilte {
  font-size: 22px;
  font-weight: 600;
  margin-bottom: 5px;
}

.resultDetailcontent {
  width: 490px;
  margin-bottom: 5px;
  font-size: 16px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  word-wrap: break-word;
}

.resultDetailcnt {
  font-size: 16px;
}
</style>
