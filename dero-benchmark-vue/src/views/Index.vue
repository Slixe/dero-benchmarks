<template>
<div id="index">
    <v-card class="bench elevation-5" :loading="loading">
        <v-card-title>
            <h2>Benchmarks</h2>
            <v-spacer></v-spacer>
            <v-text-field class="search" v-model="search" append-icon="magnify" label="Search" single-line hide-details></v-text-field>
        </v-card-title>
        <v-data-table :search="search" multi-sort :headers="headers" :items="benchmarks" :items-per-page="5">
            <template v-slot:item.timestamp="{ item }">
                <span>{{ new Date(item.timestamp).toLocaleDateString() }}</span>
            </template>
        </v-data-table>
    </v-card>
    <h4>Submit your own benchmarks <router-link to="/submit">here</router-link></h4>
    <h5>Admin panel <router-link to="/login">here</router-link></h5>
</div>
</template>

<script>
export default {
    data() {
        return {
            loading: true,
            search: "",
            headers: [
                {
                    text: "Vendor",
                    align: "start",
                    value: "vendor"
                },
                {
                    text: "Model",
                    value: "model"
                },
                {
                    text: "Memory",
                    value: "memory"
                },
                {
                    text: "Hashrate (h/s)",
                    value: "hashrate"
                },
                {
                    text: "Watts (w)",
                    value: "watts"
                },
                {
                    text: "Miner",
                    value: "minerVersion"
                },
                {
                    text: "Submitted On",
                    value: "timestamp",
                    class: "Date"
                },
                {
                    text: "User",
                    value: "owner"
                }
            ],
        benchmarks: []
        }
    },
    mounted() {
        fetch(this.$api + "/api/benchmarks").then(result => result.json()).then(json => {
            this.benchmarks = json
            this.loading = false
        })
    }
}
</script>

<style scoped>
.bench {
    margin: 10%;
    margin-top: 5%;
    margin-bottom: 2%;
    padding: 2%;
}
</style>