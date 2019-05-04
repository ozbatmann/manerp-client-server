package tr.com.manerp.business.optimization

import grails.gorm.transactions.Transactional
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.business.main.voyage.Voyage
import tr.com.manerp.business.main.voyage.VoyageService
import tr.com.manerp.optimization.OptimizationParameters

@Transactional
class OptimizationService extends BaseService
{
    VoyageService voyageService

    def save(OptimizationParameters parameters)
    {
        parameters.save(flush: true, failOnError: true)
    }

    def saveOptimizationParametersWithVoyage(OptimizationParameters parameters, Voyage voyage)
    {
        save(parameters)
        voyageService.saveVoyageWithOptimizationParameters(voyage, parameters)
    }

    def delete(OptimizationParameters parameters)
    {
        parameters.delete(flush: true, failOnError: true)
    }
}
