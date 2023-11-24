import { createApp } from 'vue'
import { createVuetify } from 'vuetify'
import '@mdi/font/css/materialdesignicons.css'
import { aliases, mdi } from 'vuetify/iconsets/mdi'

import App from './App.vue'
import router from './router'
import 'vuetify/styles'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import { createPinia } from 'pinia'
import piniaPluginPersistedState from 'pinia-plugin-persistedstate'

const vuetify = createVuetify({
  components,
  directives,

  icons: {
    defaultSet: 'mdi',
    aliases,
    sets: {
      mdi
    }
  },

  
})

const app = createApp(App)

// pinia-plugin-persistedstate
import { instance } from '@/api/axios'

app.provide('axios', instance)
app.use(createPinia().use(piniaPluginPersistedState))
app.use(router)
app.use(vuetify)
app.use(aliases)
app.use(mdi)
app.mount('#app')
