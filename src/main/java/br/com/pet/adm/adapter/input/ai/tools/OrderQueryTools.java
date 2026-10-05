package br.com.pet.adm.adapter.input.ai.tools;

import br.com.pet.adm.application.query.FindOrderByIdQuery;
import br.com.pet.adm.application.query.FindOrdersByCustomerQuery;
import br.com.pet.adm.application.query.handler.FindOrderByIdHandler;
import br.com.pet.adm.application.query.handler.FindOrdersByCustomerHandler;
import br.com.pet.adm.application.query.result.OrderDetailResult;
import br.com.pet.adm.application.query.result.OrderSummaryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.List;

/**
 * Tool adapter (somente leitura) — expõe a consulta de pedidos para o LLM.
 */
public class OrderQueryTools {

    private static final Logger log = LoggerFactory.getLogger(OrderQueryTools.class);
    private static final int PAGE_SIZE = 5;

    private final FindOrderByIdHandler findOrderByIdHandler;
    private final FindOrdersByCustomerHandler findOrdersByCustomerHandler;

    public OrderQueryTools(FindOrderByIdHandler findOrderByIdHandler,
                           FindOrdersByCustomerHandler findOrdersByCustomerHandler) {
        this.findOrderByIdHandler = findOrderByIdHandler;
        this.findOrdersByCustomerHandler = findOrdersByCustomerHandler;
    }

    @Tool(description = "Consulta os detalhes de um pedido pelo seu ID. "
            + "Use quando o usuário informar o identificador de um pedido.")
    public OrderDetailResult buscarPedidoPorId(
            @ToolParam(description = "ID do pedido") String orderId) {

        log.info("Tool call: buscarPedidoPorId(orderId={})", orderId);
        return findOrderByIdHandler.handle(new FindOrderByIdQuery(orderId));
    }

    @Tool(description = "Lista os pedidos mais recentes de um cliente pelo ID do cliente. "
            + "Retorna no máximo 5 pedidos.")
    public List<OrderSummaryResult> buscarPedidosPorCliente(
            @ToolParam(description = "ID do cliente") String customerId) {

        log.info("Tool call: buscarPedidosPorCliente(customerId={})", customerId);
        return findOrdersByCustomerHandler
                .handle(new FindOrdersByCustomerQuery(customerId, 0, PAGE_SIZE))
                .getContent();
    }
}
