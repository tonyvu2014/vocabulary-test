package com.wordcraft

import grails.transaction.Transactional

@Transactional
class WordCraftsmanService {

    def findPrincipal(String username, String password) {
        return WordCraftsman.findByUsernameAndPassword(username, password)
    }
}
