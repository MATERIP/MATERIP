<template>
  <v-card theme="dark" class="pa-8 d-flex justify-center flex-wrap">
    <v-responsive max-width="550">
      <v-img
        class="mx-auto mt-12 mb-16"
        max-height="140"
        max-width="240"
        src="https://cdn.vuetifyjs.com/docs/images/logos/vuetify-logo-dark-text.svg"
      ></v-img>

      <v-autocomplete
        :items="items"
        append-inner-icon="mdi-microphone"
        auto-select-first
        class="flex-full-width"
        density="comfortable"
        item-props
        menu-icon=""
        placeholder="Search Google or type a URL"
        prepend-inner-icon="mdi-magnify"
        rounded
        theme="light"
        variant="solo"
      ></v-autocomplete>

      
    </v-responsive>
  </v-card>
</template>
<script>
export default {
  data: () => ({
    dialog: false,
    items: [
      {
        prependIcon: "mdi-clock-outline",
        title: "recipe with chicken",
      },
      {
        prependIcon: "mdi-clock-outline",
        title: "best hiking trails near me",
      },
      {
        prependIcon: "mdi-clock-outline",
        title: "how to learn a new language",
      },
      {
        prependIcon: "mdi-clock-outline",
        title: "DIY home organization ideas",
      },
      {
        prependIcon: "mdi-clock-outline",
        title: "latest fashion trends",
      },
    ],
    shortcuts: [
      {
        icon: "mdi-github",
        title: "Master ",
        href: "https://github.com/vuetifyjs/vuetify",
      },
      {
        icon: "mdi-github",
        title: "Dev",
        href: "https://github.com/vuetifyjs/vuetify/tree/dev",
      },
      {
        icon: "mdi-github",
        title: "Stable",
        href: "https://github.com/vuetifyjs/vuetify/tree/v2-stable",
      },
      {
        icon: "mdi-github",
        title: "My Pull Requests",
        href: "https://github.com/vuetifyjs/vuetify/pulls/johnleider",
      },
    ],
  }),
};
</script>
