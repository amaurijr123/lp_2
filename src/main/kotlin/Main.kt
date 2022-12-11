import java.awt.desktop.OpenFilesEvent
import java.time.*
import java.io.*



enum class PLANO{
    AMIL, UNIMED, AMS, ASSIM, GOLDEN, OUTRO, PARTICULAR
}

 open class Contato {

     var nome: String
     var plano: PLANO
     var telefone: String
     var email: String

     constructor(n: String, p: PLANO, tel: String, em: String){
        this.nome = n
        this.plano = p
        this.telefone = tel
        this.email = em
    }
}
class Clientes{
    private var clientes: MutableMap<Contato, LocalDate>

    constructor(){
        this.clientes = HashMap()
    }
    constructor(clientes: MutableMap<Contato, LocalDate>){
        this.clientes = clientes
    }
    fun agendaCliente(contato: Contato, data: LocalDate){
        if(clientes.containsKey(contato)){
            if(clientes[contato]!!.isBefore(data)){
                clientes[contato] = data
            }
        }else{
            clientes[contato] = data
        }
    }

    override fun toString(): String {
        val entries = clientes.entries
        var retString : String = ""
        entries.forEach { i ->
            retString += i.key.nome + " " + i.value.dayOfMonth.toString() + "/" + i.value.monthValue.toString()
           println(retString)
        }
        return retString
    }
}

class ContatoEmpresa: Contato {
    var nomeEmpresa: String
    var CNPJ: String
    constructor(n: String, p: PLANO, tel: String, em: String, nomeEmpresa: String, CNPJ: String): super(n,p,tel,em){
        this.nomeEmpresa = nomeEmpresa
        this.CNPJ = CNPJ
    }
}

fun main() {
    val arq = File("save.txt")
    if(!arq.exists()){
        arq.createNewFile()
        val clientes = Clientes()
        val fulano = Contato("Fulano", PLANO.PARTICULAR, "2222222", "fulano@dominio.com")
        val beltrano = ContatoEmpresa("Beltrano", PLANO.GOLDEN, "33333333", "beltrano@dominion.com", "UFF", "11111")
        clientes.agendaCliente(fulano, LocalDate.now())
        clientes.agendaCliente(beltrano, LocalDate.now())
        val wString = clientes.toString()

        val file: OutputStream = FileOutputStream(arq)
        file.write(wString.toByteArray())


    }
    else {
        val file: InputStream = FileInputStream(arq)
        val buf: InputStream = BufferedInputStream(file)
        val input: ObjectInput = ObjectInputStream(buf)
        input.use { clientes ->
            clientes.readObject() as? Clientes
            clientes.toString()
        }
    }
}