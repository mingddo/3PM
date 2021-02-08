<template>
  <splide :options="options">
    <splide-slide v-for="(op, idx) in c" :key="idx">
      <GroupRecommendCard />
    </splide-slide>
  </splide>
</template>

<script>
import "@/assets/css/group_splide.css";
import GroupRecommendCard from "./GroupRecommendCard.vue";

export default {
  components: { GroupRecommendCard },
  data() {
    return {
      c: [{}, {}, {}, {}, {}, {}, {}],
      options: {
        rewind: true,
        type: "loop",
        perPage: 2,
        padding: {
          right: "50px",
          left: "50px",
        },
        pagination: true,

        classes: { pagination: "splide__pagination" },
      },
    };
  },
};
</script>

<style scoped lang="css" src="@/assets/css/group_splide.css"></style>
