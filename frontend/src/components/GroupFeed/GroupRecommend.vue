<template>
  <div>
    <div class="group-reco-header">
      이런 그룹은 어떠신가요?
    </div>
    <GroupRecoSplide :recoGroup="recoGroup"/>
  </div>

</template>

<script>
import GroupRecoSplide from './GroupRecoSplide.vue';


export default {
  components : {
    GroupRecoSplide
  },
  props: {
    recoGroup: Array,
  },
  data() {
    return {
      // c: this.recoGroup,
      // options: {
      //   rewind: true,
      //   type: "loop",
      //   perPage: 2,
      //   padding: {
      //     right: "50px",
      //     left: "50px",
      //   },
      //   pagination: true,

      //   classes: { pagination: "splide__pagination" },
      // },
    };
  },
};
</script>

<style>
.group-reco-header {
  padding-top : 40px; 
  padding-bottom:40px; 
  text-align:center;
  font-size: 24px;
}
</style>
