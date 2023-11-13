import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { createVuetify } from 'vuetify'
import '@mdi/font/css/materialdesignicons.css'
import { aliases, mdi } from 'vuetify/iconsets/mdi'

import App from './App.vue'
import router from './router'
import 'vuetify/styles'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

const vuetify = createVuetify({
  components,
  directives,

  icons: {
    defaultSet: 'mdi',
    aliases,
    sets: {
      mdi
    }
  }
})

const app = createApp(App)
import axios from 'axios'
const { VITE_SERVER_URL } = import.meta.env
const instance = axios.create({
  baseURL: VITE_SERVER_URL,
  withCredentials: true
})
app.provide('axios', instance)

app.use(createPinia())
app.use(router)
app.use(vuetify)
app.use(aliases)
app.use(mdi)

app.mount('#app')
