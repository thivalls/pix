package br.com.zup.pix.validation

import io.micronaut.validation.validator.constraints.EmailValidator


enum class KeyTypeValidation {
    CPF {
        override fun valida(key: String?): Boolean {
            if(key.isNullOrBlank()) {
                return false
            }

            if(!key.matches("^[0-9]{11}\$".toRegex())) {
                return false
            }

            return CPFValidation
//            return CPF .run {
//                initialize(null)
//                isValid(key, null)
//            }
            return true
        }
    },
    CELULAR {
        override fun valida(key: String?): Boolean {
            if(key.isNullOrBlank()) {
                return false
            }
            return key.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
        }
    },
    EMAIL {
        override fun valida(key: String?): Boolean {
            if(key.isNullOrBlank()) {
                return false
            }
            return EmailValidator().run {
                initialize(null)
                isValid(key, null)
            }
        }
    },
    ALEATORIA {
        override fun valida(key: String?) = key.isNullOrBlank()
    };

    abstract fun valida(key: String?): Boolean
}