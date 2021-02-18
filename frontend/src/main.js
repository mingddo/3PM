import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VueCarousel from 'vue-carousel';
import VueSplide from '@splidejs/vue-splide';
import VTooltip from 'v-tooltip'
import VueRx from 'vue-rx'

import VuejsClipper from "vuejs-clipper/dist/vuejs-clipper.umd";
import "vuejs-clipper/dist/vuejs-clipper.css";
import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';


const options = {
  confirmButtonColor: '#d33',
  cancelButtonColor: '#b29887', 
  width: '250px',
  position: 'top',
};
// install vue-sweetalert2
Vue.use(VueSweetalert2, options);

// install vue-rx
Vue.use(VueRx)
// install vuejs-clipper
Vue.use(VuejsClipper,{
  components: {
     clipperBasic: false,
     clipperPreview: true
  }
 })

Vue.config.productionTip = false
Vue.use(VueCarousel);
Vue.use( VueSplide );
Vue.use(VTooltip)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
