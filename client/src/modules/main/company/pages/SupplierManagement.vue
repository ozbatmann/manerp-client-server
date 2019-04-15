<template>
    <v-container fluid>
        <supplier-management-table></supplier-management-table>
    </v-container>
</template>

<script>
    import supplierManagementTable from '@/modules/main/company/components/supplierManagementTable'

    export default {
        name: "SupplierManagement",
        components: {
            supplierManagementTable
        }
    }
</script>

<style scoped>

</style>