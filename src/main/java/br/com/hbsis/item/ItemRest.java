package br.com.hbsis.item;

import br.com.hbsis.categoria.CategoriaRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
public class ItemRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaRest.class);
    private final ItemService itemService;

    @Autowired
    public ItemRest(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ItemDTO save(@RequestBody ItemDTO itemDTO) {
        LOGGER.info("Recebendo solicitação de item...");
        LOGGER.debug("Payaload: {}", itemDTO);

        return this.itemService.save(itemDTO);
    }

    @GetMapping("/{id}")
    public ItemDTO find(@PathVariable("id") Long id) {

        LOGGER.info("Recebendo find by ID... id: [{}]", id);

        return this.itemService.findById(id);
    }

    @PutMapping("/{id}")
    public ItemDTO udpate(@PathVariable("id") Long id, @RequestBody ItemDTO itemDTO) {
        LOGGER.info("Recebendo Update de item, id: {}", id);
        LOGGER.debug("Payload: {}", itemDTO);

        return this.itemService.update(itemDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete de item de ID: {}", id);

        this.itemService.delete(id);
    }



}
