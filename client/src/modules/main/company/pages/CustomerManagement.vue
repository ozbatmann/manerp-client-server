<template>
    <v-container fluid>
        <customer-management-table></customer-management-table>
    </v-container>
</template>

<script>
    import customerManagementTable from '@/modules/main/company/components/customerManagementTable'

    export default {
        name: "CustomerManagement",
        components: {
            customerManagementTable
        }
    }
</script>

<style scoped>

</style>